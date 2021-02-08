package fr.isen.amadori.androiderestaurant.map
import android.content.Intent
import android.net.Uri
import com.google.android.gms.maps.GoogleMap
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import fr.isen.amadori.androiderestaurant.BaseActivity
import fr.isen.amadori.androiderestaurant.R
import fr.isen.amadori.androiderestaurant.databinding.ActivityMapBinding

private lateinit var binding: ActivityMapBinding

class MapActivity : BaseActivity(), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (getString(R.string.maps_api_key).isEmpty()) {
            Toast.makeText(this, "Add your own API key in MapWithMarker/app/secure.properties as MAPS_API_KEY=YOUR_API_KEY", Toast.LENGTH_LONG).show()
        }
       val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        binding.buttonTempo.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse("https://www.google.com/maps?q=google+map+ISEN+TOULON&um=1&ie=UTF-8&sa=X&ved=2ahUKEwi5ycDAstvuAhUyyYUKHQ2nCZMQ_AUoAXoECAgQAw"))
            startActivity(intent)
        }
    }

    override fun onMapReady(p0: GoogleMap?) {
        p0?.apply {
            val sydney = LatLng(-33.852, 151.211)
            addMarker(
                MarkerOptions()
                    .position(sydney)
                    .title("Marker in Sydney")
            )
        }
    }
}