package com.example.musikaja11;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.app.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private String filePath = "/storage/emulated/0/Music/song1.mp3"; // Ubah path ini sesuai lokasi file Anda

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Memuat fragment default (SongsFragment)
        if (savedInstanceState == null) {
            loadFragment(new SongsFragment());
        }

        // Mengatur BottomNavigationView dan listener
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.nav_songs:
                    selectedFragment = new SongsFragment();
                    break;
                case R.id.nav_folders:
                    selectedFragment = new FoldersFragment();
                    break;
                case R.id.nav_albums:
                    selectedFragment = new AlbumsFragment();
                    break;
                case R.id.nav_settings:
                    selectedFragment = new SettingsFragment();
                    break;
            }
            if (selectedFragment != null) {
                loadFragment(selectedFragment);
            }
            return true;
        });

        // Tombol Play dan Pause untuk memutar audio
        setupAudioControls();
    }

    // Fungsi untuk mengatur tombol audio (Play dan Pause)
    private void setupAudioControls() {
        // Tombol Play
        Button playButton = findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudioFromFile();
            }
        });

        // Tombol Pause
        Button pauseButton = findViewById(R.id.pause_button);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    Toast.makeText(MainActivity.this, "Playback paused", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Fungsi untuk memutar audio dari file
    private void playAudioFromFile() {
        try {
            if (mediaPlayer == null) {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(filePath); // Path file di penyimpanan lokal
                mediaPlayer.prepare();
            }
            mediaPlayer.start();
            Toast.makeText(this, "Playback started", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // Fungsi untuk memuat fragment
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release(); // Lepaskan resource MediaPlayer
            mediaPlayer = null;
        }
    }
}
