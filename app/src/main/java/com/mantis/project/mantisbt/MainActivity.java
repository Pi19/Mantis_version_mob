package com.mantis.project.mantisbt;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mantis.project.mantisbt.Class.Url;
import com.mantis.project.mantisbt.fragment.Accueil;
import com.mantis.project.mantisbt.fragment.Add_issue;
import com.mantis.project.mantisbt.fragment.List_issue;
import com.mantis.project.mantisbt.fragment.user;

public class MainActivity extends AppCompatActivity implements List_issue.OnFragmentInteractionListener
        ,user.OnFragmentInteractionListener,Add_issue.OnFragmentInteractionListener ,Accueil.OnFragmentInteractionListener {

    private TextView mTextMessage;
    Fragment fragment;
    FloatingActionButton btnsettings;
    Button btnvalider;
    MenuItem test;
    TextView ip ;
    public static final String IP = "ip" ;
    SharedPreferences sharedPreferences ;
    String noteTobogue ;
    String issueTobogue ;

    //final ProgressBar simpleProgressBar = (ProgressBar) findViewById(R.id.simpleProgressBar);
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_home:
                    Accueil accueil = new Accueil();
                    FragmentManager frgaccueil= getSupportFragmentManager();
                    frgaccueil.beginTransaction().replace(R.id.content_main, accueil).commit();
                  return true;
                case R.id.bogue:
                   List_issue list = new List_issue();
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.content_main, list).commit();
                    //simpleProgressBar.setVisibility(View.VISIBLE);
                   return true;

                case R.id.addissue:
                    Add_issue add_issue = new Add_issue();
                    FragmentManager manager_add = getSupportFragmentManager();
                    manager_add.beginTransaction().replace(R.id.content_main,add_issue).commit();
                    return true;
                case R.id.navigation_notifications:
                    user user = new user();
                    FragmentManager test2= getSupportFragmentManager();
                    test2.beginTransaction().replace(R.id.content_main, user).commit();
                    return true;


            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //MenuItem test = ((BottomNavigationItemView) findViewById(R.id.bogue));


        Intent i = getIntent();
        noteTobogue = i.getStringExtra("noteTobogue");//retour venant de l'activité AddNote
        issueTobogue = i.getStringExtra("issueTolist");
        if(noteTobogue != null) {
            mOnNavigationItemSelectedListener.onNavigationItemSelected(new MenuItem() {
                @Override
                public int getItemId() {
                    return R.id.bogue;
                }

                @Override
                public int getGroupId() {
                    return 0;
                }

                @Override
                public int getOrder() {
                    return 0;
                }

                @Override
                public MenuItem setTitle(CharSequence title) {
                    return null;
                }

                @Override
                public MenuItem setTitle(int title) {
                    return null;
                }

                @Override
                public CharSequence getTitle() {
                    return null;
                }

                @Override
                public MenuItem setTitleCondensed(CharSequence title) {
                    return null;
                }

                @Override
                public CharSequence getTitleCondensed() {
                    return null;
                }

                @Override
                public MenuItem setIcon(Drawable icon) {
                    return null;
                }

                @Override
                public MenuItem setIcon(int iconRes) {
                    return null;
                }

                @Override
                public Drawable getIcon() {
                    return null;
                }

                @Override
                public MenuItem setIntent(Intent intent) {
                    return null;
                }

                @Override
                public Intent getIntent() {
                    return null;
                }

                @Override
                public MenuItem setShortcut(char numericChar, char alphaChar) {
                    return null;
                }

                @Override
                public MenuItem setNumericShortcut(char numericChar) {
                    return null;
                }

                @Override
                public char getNumericShortcut() {
                    return 0;
                }

                @Override
                public MenuItem setAlphabeticShortcut(char alphaChar) {
                    return null;
                }

                @Override
                public char getAlphabeticShortcut() {
                    return 0;
                }

                @Override
                public MenuItem setCheckable(boolean checkable) {
                    return null;
                }

                @Override
                public boolean isCheckable() {
                    return false;
                }

                @Override
                public MenuItem setChecked(boolean checked) {
                    return null;
                }

                @Override
                public boolean isChecked() {
                    return false;
                }

                @Override
                public MenuItem setVisible(boolean visible) {
                    return null;
                }

                @Override
                public boolean isVisible() {
                    return false;
                }

                @Override
                public MenuItem setEnabled(boolean enabled) {
                    return null;
                }

                @Override
                public boolean isEnabled() {
                    return false;
                }

                @Override
                public boolean hasSubMenu() {
                    return false;
                }

                @Override
                public SubMenu getSubMenu() {
                    return null;
                }

                @Override
                public MenuItem setOnMenuItemClickListener(OnMenuItemClickListener menuItemClickListener) {
                    return null;
                }

                @Override
                public ContextMenu.ContextMenuInfo getMenuInfo() {
                    return null;
                }

                @Override
                public void setShowAsAction(int actionEnum) {

                }

                @Override
                public MenuItem setShowAsActionFlags(int actionEnum) {
                    return null;
                }

                @Override
                public MenuItem setActionView(View view) {
                    return null;
                }

                @Override
                public MenuItem setActionView(int resId) {
                    return null;
                }

                @Override
                public View getActionView() {
                    return null;
                }

                @Override
                public MenuItem setActionProvider(ActionProvider actionProvider) {
                    return null;
                }

                @Override
                public ActionProvider getActionProvider() {
                    return null;
                }

                @Override
                public boolean expandActionView() {
                    return false;
                }

                @Override
                public boolean collapseActionView() {
                    return false;
                }

                @Override
                public boolean isActionViewExpanded() {
                    return false;
                }

                @Override
                public MenuItem setOnActionExpandListener(OnActionExpandListener listener) {
                    return null;
                }
            });
            navigation.setSelectedItemId(R.id.bogue);
        }
        if(issueTobogue !=null)
        {
            mOnNavigationItemSelectedListener.onNavigationItemSelected(new MenuItem() {
                @Override
                public int getItemId() {
                    return R.id.bogue;
                }

                @Override
                public int getGroupId() {
                    return 0;
                }

                @Override
                public int getOrder() {
                    return 0;
                }

                @Override
                public MenuItem setTitle(CharSequence title) {
                    return null;
                }

                @Override
                public MenuItem setTitle(int title) {
                    return null;
                }

                @Override
                public CharSequence getTitle() {
                    return null;
                }

                @Override
                public MenuItem setTitleCondensed(CharSequence title) {
                    return null;
                }

                @Override
                public CharSequence getTitleCondensed() {
                    return null;
                }

                @Override
                public MenuItem setIcon(Drawable icon) {
                    return null;
                }

                @Override
                public MenuItem setIcon(int iconRes) {
                    return null;
                }

                @Override
                public Drawable getIcon() {
                    return null;
                }

                @Override
                public MenuItem setIntent(Intent intent) {
                    return null;
                }

                @Override
                public Intent getIntent() {
                    return null;
                }

                @Override
                public MenuItem setShortcut(char numericChar, char alphaChar) {
                    return null;
                }

                @Override
                public MenuItem setNumericShortcut(char numericChar) {
                    return null;
                }

                @Override
                public char getNumericShortcut() {
                    return 0;
                }

                @Override
                public MenuItem setAlphabeticShortcut(char alphaChar) {
                    return null;
                }

                @Override
                public char getAlphabeticShortcut() {
                    return 0;
                }

                @Override
                public MenuItem setCheckable(boolean checkable) {
                    return null;
                }

                @Override
                public boolean isCheckable() {
                    return false;
                }

                @Override
                public MenuItem setChecked(boolean checked) {
                    return null;
                }

                @Override
                public boolean isChecked() {
                    return false;
                }

                @Override
                public MenuItem setVisible(boolean visible) {
                    return null;
                }

                @Override
                public boolean isVisible() {
                    return false;
                }

                @Override
                public MenuItem setEnabled(boolean enabled) {
                    return null;
                }

                @Override
                public boolean isEnabled() {
                    return false;
                }

                @Override
                public boolean hasSubMenu() {
                    return false;
                }

                @Override
                public SubMenu getSubMenu() {
                    return null;
                }

                @Override
                public MenuItem setOnMenuItemClickListener(OnMenuItemClickListener menuItemClickListener) {
                    return null;
                }

                @Override
                public ContextMenu.ContextMenuInfo getMenuInfo() {
                    return null;
                }

                @Override
                public void setShowAsAction(int actionEnum) {

                }

                @Override
                public MenuItem setShowAsActionFlags(int actionEnum) {
                    return null;
                }

                @Override
                public MenuItem setActionView(View view) {
                    return null;
                }

                @Override
                public MenuItem setActionView(int resId) {
                    return null;
                }

                @Override
                public View getActionView() {
                    return null;
                }

                @Override
                public MenuItem setActionProvider(ActionProvider actionProvider) {
                    return null;
                }

                @Override
                public ActionProvider getActionProvider() {
                    return null;
                }

                @Override
                public boolean expandActionView() {
                    return false;
                }

                @Override
                public boolean collapseActionView() {
                    return false;
                }

                @Override
                public boolean isActionViewExpanded() {
                    return false;
                }

                @Override
                public MenuItem setOnActionExpandListener(OnActionExpandListener listener) {
                    return null;
                }
            });
            navigation.setSelectedItemId(R.id.bogue);

        }




        btnsettings =  findViewById(R.id.settings);
        btnsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.settings,null);
                ip = (TextView)  mView.findViewById(R.id.ip_addr);

                mBuilder.setPositiveButton("Valide IP", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //dialog.dismiss();
                    }
                });
                mBuilder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                //Url url = new Url(ip.toString());

                mBuilder.setView(mView);
                final AlertDialog d = mBuilder.create();
                d.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(final DialogInterface dialog) {
                        Button b = d.getButton(AlertDialog.BUTTON_POSITIVE);
                        b.setHeight(30);
                        Button c = d.getButton(AlertDialog.BUTTON_NEGATIVE);
                        c.setHeight(30);
                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            String chaine = ip.getText().toString();
                            Log.d("MANTIS",chaine);
                            //Url url = new Url(chaine);

                            sharedPreferences = getSharedPreferences(IP,0);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("IPaddr",chaine);
                            editor.commit();
                            dialog.cancel();
                            }
                        });
                    }
                });

                d.show();
            }
        });


        //btnvalider = findViewById(R.id.validerip);
        //btnvalider.setOnClickListener();
        //mOnNavigationItemSelectedListener.onNavigationItemSelected();

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
