package illinois.nao.nao;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import illinois.nao.nao.Pages.NewsfeedFragment;
import illinois.nao.nao.Pages.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    static private final int REQUEST_TAKE_PHOTO = 1;
    static private final int REQUEST_RECORD_VIDEO = 2;

    private FragmentManager fragmentManager;

    AHBottomNavigation bottomNavigation;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // toolbar_content.setNavigationIcon(R.drawable.icon);

        setSupportActionBar(toolbar);

        // ********* SET UP BOTTOM NAVIGATION ********
        // Create items
        AHBottomNavigationItem newsfeed = new AHBottomNavigationItem(R.string.newsfeed,
                R.drawable.ic_event_note_black_24dp, R.color.cardview_light_background);

        AHBottomNavigationItem profile = new AHBottomNavigationItem(R.string.profile,
                R.drawable.ic_person_black_24dp, R.color.cardview_light_background);

        AHBottomNavigationItem search = new AHBottomNavigationItem(R.string.search,
                R.drawable.ic_search_black_24dp, R.color.cardview_light_background);

        bottomNavigation.addItem(search);
        bottomNavigation.addItem(profile);
        bottomNavigation.addItem(newsfeed);

        bottomNavigation.setCurrentItem(1);

        // Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch(position) {
                    case 0:
                        // Search
                        goToSearch();
                        break;
                    case 1:
                        // Profile
                        goToProfile();
                        break;
                    case 2:
                        // Newsfeed
                        goToNewsfeed();
                        break;
                }
                return true;
            }
        });

        // *******************************************

        fragmentManager = getSupportFragmentManager();

        // Our starting page
        goToProfile();
    }

    private void goToSearch() {
        fragmentManager.beginTransaction()
                .replace(R.id.content_holder, new NewsfeedFragment()).commit();
    }

    private void goToProfile() {
        fragmentManager.beginTransaction()
                .replace(R.id.content_holder, new ProfileFragment()).commit();
    }

    private void goToNewsfeed() {
        fragmentManager.beginTransaction()
                .replace(R.id.content_holder, new NewsfeedFragment()).commit();
    }

    public void addPicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "illinois.nao.nao.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    public void addVideo() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_RECORD_VIDEO);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        return image;

    }
}
