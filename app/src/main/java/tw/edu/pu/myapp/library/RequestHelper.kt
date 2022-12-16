package tw.edu.pu.myapp.library

import android.content.Context
import android.location.LocationManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import tw.edu.pu.myapp.R

class RequestHelper(private val context: Context) : MultiplePermissionsListener {

    fun requestLocation() {
        Dexter.withContext(context)
            .withPermissions(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
            ).withListener(this)
            .check()
    }

    fun checkGPS(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
        p0.let {
            if (p0?.areAllPermissionsGranted() == false) {
                MaterialAlertDialogBuilder(context, R.style.Style_CustomDialog)
                    .setTitle(context.getString(R.string.no_permission_title))
                    .setMessage(context.getString(R.string.no_permission_message))
                    .setPositiveButton(context.getString(R.string.positive_button)) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }
    }

    override fun onPermissionRationaleShouldBeShown(
        p0: MutableList<PermissionRequest>?,
        p1: PermissionToken?
    ) {
        p1?.continuePermissionRequest()
    }
}