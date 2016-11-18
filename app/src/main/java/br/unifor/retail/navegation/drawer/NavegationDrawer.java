package br.unifor.retail.navegation.drawer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import br.unifor.retail.R;
import br.unifor.retail.view.activity.CartActivity_;
import br.unifor.retail.view.activity.HistoryActivity_;
import br.unifor.retail.view.activity.InfoClientActivity_;
import br.unifor.retail.view.activity.LoginActivity_;
import br.unifor.retail.view.activity.MyProductActivity_;

import static com.facebook.AccessToken.getCurrentAccessToken;

/**
 * Created by mafra on 01/11/16.
 */

public class NavegationDrawer {

    private Drawer.Result navigationDrawerLeft;
    private AccountHeader.Result headerNavigationLeft;
    private Toolbar toolbar;
    private Activity activity;
    private String userId;
    private String name;
    private String grafiUrl;
    private String profileImgUrl;
    private Bundle bundlex;
    private String email;
    private String first_name;
    private String last_name;
    private Bundle bFacebookData;


//    private OnCheckedChangeListener mOnCheckedChangeListener = new OnCheckedChangeListener(){
//        @Override
//        public void onCheckedChanged(IDrawerItem iDrawerItem, CompoundButton compoundButton, boolean b) {
//            Toast.makeText(activity, "onCheckedChanged: "+( b ? "true" : "false" ), Toast.LENGTH_SHORT).show();
//        }
//    };

    public NavegationDrawer(Toolbar toolbar, Activity activity) {
        this.toolbar = toolbar;
        this.activity = activity;
    }

    public void createNavigationDrawer() {
        //NAVIGATION DRAWER
        headerNavigationLeft = new AccountHeader()
                .withActivity(activity)
                .withCompactStyle(false)
//                .withSavedInstance(savedInstanceState)
                .withThreeSmallProfileImages(true)
                .withHeaderBackground(R.drawable.menu)
                .addProfiles(
                        new ProfileDrawerItem().withName(name).withEmail("vania.almeida28@hotmail.com"))
                .build();


        navigationDrawerLeft = new Drawer()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withDisplayBelowToolbar(false)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerGravity(Gravity.LEFT)
//                .withSavedInstance(savedInstanceState)
                .withSelectedItem(-2)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(headerNavigationLeft)
                .withOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
                    @Override
                    public boolean onNavigationClickListener(View view) {
                        return false;
                    }
                })


//                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
//                    @Override
//                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
//                        Toast.makeText(activity, "onItemLongClick: " + i, Toast.LENGTH_SHORT).show();
//                        return false;
//                    }
//                })


                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Perfil").withIcon(activity.getResources().getDrawable(R.drawable.perfil)),
                        new PrimaryDrawerItem().withName("Carrinho").withIcon(activity.getResources().getDrawable(R.drawable.carrinho)),
                        new PrimaryDrawerItem().withName("Meus pedidos").withIcon(activity.getResources().getDrawable(R.drawable.pedidos)),
                        new PrimaryDrawerItem().withName("Histórico de itens visualizados").withIcon(activity.getResources().getDrawable(R.drawable.visualizacao)),
                        new PrimaryDrawerItem().withName("Sair").withIcon(activity.getResources().getDrawable(R.drawable.sair))
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        switch (i) {
                            case 0:
                                Intent intent = new Intent(activity, InfoClientActivity_.class);
                                activity.startActivity(intent);
                                break;
                            case 1:
                                Intent intent1 = new Intent(activity, CartActivity_.class);
                                activity.startActivity(intent1);
                                break;
                            case 2:
                                Intent intent2 = new Intent(activity, MyProductActivity_.class);
                                activity.startActivity(intent2);
                                break;
                            case 3:
                                Intent intent3 = new Intent(activity, HistoryActivity_.class);
                                activity.startActivity(intent3);
                                break;
                            case 4:
                                LoginManager.getInstance().logOut();
                                goLoginScreen();
                                break;


                        }
                    }
                })
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        Toast.makeText(activity, "onItemLongClick: " + i, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                .build();

    }

    private void goLoginScreen() {
        Intent intent = new Intent(activity, LoginActivity_.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    public void getProfile() {
        Profile profile = Profile.getCurrentProfile();
        if (getCurrentAccessToken() != null) {

            String accessToken = AccessToken.getCurrentAccessToken().getUserId().toString();
            Log.d("Teste", AccessToken.getCurrentAccessToken().getUserId().toString());


            userId = AccessToken.getCurrentAccessToken().getUserId().toString();
            profileImgUrl = "https://graph.facebook.com/" + userId + "/picture?type=large";
            grafiUrl = "https://graph.facebook.com/me?access_token=" + AccessToken.getCurrentAccessToken().getToken();

            if (profile != null)
                name = profile.getName();

            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            Log.i("LoginActivity", response.toString());
                            // Get facebook data from login
                            bFacebookData = getFacebookData(object);

                            Log.i("LoginActhrth", bFacebookData.toString());
                            Log.i("NOME", bFacebookData.getString("first_name"));
                            Log.i("EMAIL", bFacebookData.getString("email"));

                            String emailaqui = bFacebookData.getString("email");

                        }

                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
            request.setParameters(parameters);
            request.executeAsync();
        }
    }


    private Bundle getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));

            return bundle;
        } catch (JSONException e) {
            Log.d("xxxx", "Error parsing JSON");
        }
        return bundlex;
    }
}

