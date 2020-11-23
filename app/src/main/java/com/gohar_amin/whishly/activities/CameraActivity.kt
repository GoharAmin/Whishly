package com.gohar_amin.whishly.activities

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.gohar_amin.whishly.R
import com.google.ar.core.*
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import java.io.File
import java.util.*


class CameraActivity : AppCompatActivity() {
    private lateinit var fragment:ArFragment
    private lateinit var model:ModelRenderable

  /*  private var mSession: Session? = null
    private var filter: CameraConfigFilter? = null
    private var mUserRequestedInstall = true
    private var cameraConfigList: MutableList<CameraConfig>? = null
    private var sharedCamera: SharedCamera? = null
    private var cameraId: String? = null*/

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        fragment=supportFragmentManager.findFragmentById(R.id.fragment) as ArFragment
        setUpModel()
        setUpPlane()
    }

    private fun setUpPlane() {
        fragment.setOnTapArPlaneListener { hitResult, plane, motionEvent ->
            val createAnchor = hitResult.createAnchor()
            val node=AnchorNode(createAnchor)
            node.setParent(fragment.arSceneView.scene)
            createModel(node)
        }
    }

    private fun createModel(node: AnchorNode) {
        val transformableNode = TransformableNode(fragment.transformationSystem)
        transformableNode.setParent(node)
        transformableNode.renderable=model
        transformableNode.select()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setUpModel() {
        ModelRenderable.builder()
                .setSource(this,R.raw.sceneform_plane_shadow_material)
                .build()
                .thenAccept {
                    model=it
                }.exceptionally {t->
                    Toast.makeText(this@CameraActivity, ""+t.message, Toast.LENGTH_SHORT).show()
                    return@exceptionally null
                }
    }

    override fun onResume() {
        super.onResume()
        /*try {
            if (mSession == null)
                when (ArCoreApk.getInstance().requestInstall(this, mUserRequestedInstall)) {
                    ArCoreApk.InstallStatus.INSTALLED -> {
                        Log.e("CameraActivity","Installed")
                        mSession = Session(this, EnumSet.of(Session.Feature.SHARED_CAMERA))
                        sharedCamera = mSession!!.getSharedCamera()
                        filter = CameraConfigFilter(mSession)
                        filter!!.targetFps = EnumSet.of(CameraConfig.TargetFps.TARGET_FPS_30)
                        filter!!.depthSensorUsage =
                            EnumSet.of(CameraConfig.DepthSensorUsage.DO_NOT_USE)
                        cameraConfigList = mSession!!.getSupportedCameraConfigs(filter)
                        cameraId = mSession!!.getCameraConfig().getCameraId();
                        cameraConfigList?.let {
                            mSession!!.cameraConfig = it[0]
                            val sceneViewerIntent = Intent(Intent.ACTION_VIEW)
                            val intentUri =
                                Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                    .appendQueryParameter(
                                        "file",
                                        "https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/Avocado/glTF/Avocado.gltf"
                                    )
                                    .appendQueryParameter("mode", "ar_preferred")
                                    .build()
                            sceneViewerIntent.data = intentUri
                            sceneViewerIntent.setPackage("com.google.ar.core")
                            startActivity(sceneViewerIntent)
                        }
                    }
                    ArCoreApk.InstallStatus.INSTALL_REQUESTED -> {
                        Log.e("CameraActivity","Not Installed")
                        mUserRequestedInstall = false;
                    }
                }
        } catch (e: UnavailableUserDeclinedInstallationException) {
            Toast.makeText(this, "" + e.message, Toast.LENGTH_SHORT).show()
        } catch (ex: Exception) {
            Log.e("CameraActivity", "" + ex.message);
            return
        }*/

    }

  /*  fun startRecording() {
        if(mSession!=null) {
            val destination: String = File(getFilesDir(), "recording.mp4").getAbsolutePath()
            val recordingConfig = RecordingConfig(mSession)
                .setMp4DatasetFilePath(destination)
                .setAutoStopOnPause(true)
            try {
                mSession!!.startRecording(recordingConfig)
            } catch (e: RecordingFailedException) {
                Log.e("CameraActivity", "Failed to start recording", e)
            }
        }else{
            Log.e("CameraActivity", "Please try again")
        }
        //session.resume()
    }
    fun stopRecording(){
        if(mSession!=null){
            try {
                mSession!!.stopRecording() // Stop recording.
            } catch (e: RecordingFailedException) {
                Log.e("CameraActivity", "Failed to stop recording", e)
            }
        }
    }*/
}