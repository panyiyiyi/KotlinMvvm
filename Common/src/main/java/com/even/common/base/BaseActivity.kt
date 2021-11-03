package com.even.common.base

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.even.common.impl.OnPermissionCallBack
import com.even.common.impl.OnPermissionCallBacks
import com.even.common.utils.ActivityManagerUtils
import java.lang.reflect.ParameterizedType

/**
 *  @author  Even
 *  @date   2021/10/19
 */
abstract class BaseActivity<VM : BaseViewModel, T : ViewDataBinding>
    (
    @LayoutRes private val layoutId: Int,
    private val variableId: Int
) :
    AppCompatActivity() {
    lateinit var viewModel: VM
    lateinit var dataBinding: T
    private var permissionCallBacks: OnPermissionCallBacks? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = createViewModel()

        dataBinding = DataBindingUtil.inflate(layoutInflater, layoutId, null, false)
        dataBinding.lifecycleOwner = this
        dataBinding.setVariable(variableId, viewModel)

        val view = if (useDefaultTitleBar()) {
            //使用默认标题
            val linearLayout = LinearLayout(this)
            linearLayout.addView(
                getTitleBarView(), LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            )
            linearLayout.orientation = LinearLayout.VERTICAL
            linearLayout.addView(
                dataBinding.root, LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
            )
            linearLayout
        } else {
            //不使用默认标题
            dataBinding.root
        }
        setContentView(view)
        ActivityManagerUtils.addActivity(this)
        initView(view as ViewGroup)
        initData()
    }

    fun createViewModel(): VM {
        val genericSuperclass = javaClass.genericSuperclass
        val parameterizedType = genericSuperclass as ParameterizedType
        val actualTypeArguments = parameterizedType.actualTypeArguments
        val viewModelClass: Class<VM> = actualTypeArguments[0] as Class<VM>
        return ViewModelProvider(this)[viewModelClass]
    }

    override fun onDestroy() {
        super.onDestroy()
        permissionCallBacks = null
        ActivityManagerUtils.closeActivity(this)
    }

    /**
     * 单个权限申请
     */
    @Synchronized
    open fun requestPermission(permission: String, callBack: OnPermissionCallBack) {
        requestPermissions(mutableListOf(permission), object : OnPermissionCallBacks {
            override fun onFailResult(denies: Array<String>) {
                callBack.onResult(false)
            }

            override fun onSuccessResult() {
                callBack.onResult(true)
            }
        })

    }

    /**
     * 多权限申请
     */
    @Synchronized
    open fun requestPermissions(
        permissions: MutableList<String>,
        callBacks: OnPermissionCallBacks
    ) {
        val refuseLists = mutableListOf<String>()
        this.permissionCallBacks = callBacks
        var isRequest = false
        permissions.forEach {
            if (ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED) {
                //有一个没有通过就发起请求
                isRequest = true
                refuseLists.add(it)
            }
        }
        if (isRequest) {
            ActivityCompat.requestPermissions(
                this,
                refuseLists.toTypedArray(),
                REQ_PERMISSION_RECODE
            )
        } else {
            permissionCallBacks?.onSuccessResult()
        }
    }

    /**
     * 申请悬浮窗权限
     */
    open fun requestOverlays() {
        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            intent.data = Uri.fromParts("package", this.packageName, null)
            startActivityForResult(intent, REQ_OVERLAY_PERMISSION)
        } else {
            resultOverlaysPermission(true)
        }
    }

    /**
     * 申请悬浮窗权限结果
     * @param isAgree 是否同意
     */
    open fun resultOverlaysPermission(isAgree: Boolean) {

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (REQ_PERMISSION_RECODE == requestCode) {
            val deniedLists = mutableListOf<String>()
            for (index in grantResults.indices) {
                if (PackageManager.PERMISSION_DENIED == grantResults[index]) {
                    deniedLists.add(permissions[index])
                }
            }
            if (deniedLists.size > 0) {
                permissionCallBacks?.onFailResult(deniedLists.toTypedArray())
            } else {
                permissionCallBacks?.onSuccessResult()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_OVERLAY_PERMISSION) {
            if (Settings.canDrawOverlays(this)) {
                resultOverlaysPermission(true)
            } else {
                resultOverlaysPermission(false)
            }
        }
    }


    //set title bar view
    abstract fun getTitleBarView(): View

    //init layout
    abstract fun initView(view: ViewGroup)

    //init data
    open fun initData() {}


    //use default title bar
    open fun useDefaultTitleBar(): Boolean = true

    companion object {
        //权限请求码
        const val REQ_PERMISSION_RECODE = 0x123

        //悬浮窗
        const val REQ_OVERLAY_PERMISSION = 0x124
    }

}