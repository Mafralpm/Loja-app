package br.unifor.retail.navegation.drawer;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.AppCompatDrawableManager;
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

import org.json.JSONObject;

import br.unifor.retail.R;
import br.unifor.retail.view.activity.CartActivity;
import br.unifor.retail.view.activity.HistoryActivity;
import br.unifor.retail.view.activity.InfoClientActivity;
import br.unifor.retail.view.activity.LoginActivity;
import br.unifor.retail.view.activity.My_ProductActivity;

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
                        new ProfileDrawerItem().withName(name).withEmail("vania.almeida28@hotmail.com").withIcon(profileImgUrl))
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
                   /*.withOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
                       @Override
                       public boolean onNavigationClickListener(View view) {
                           return false;
                       }
                   })*/
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        switch (i) {
                            case 0:
                                Intent intent = new Intent(activity, InfoClientActivity.class);
                                activity.startActivity(intent);
                                break;
                            case 1:
                                Intent intent1 = new Intent(activity, CartActivity.class);
                                activity.startActivity(intent1);
                                break;
                            case 2:
                                Intent intent2 = new Intent(activity, My_ProductActivity.class);
                                activity.startActivity(intent2);
                                break;
                            case 3:
                                Intent intent3 = new Intent(activity, HistoryActivity.class);
                                activity.startActivity(intent3);
                                break;
                            case 4:
                                LoginManager.getInstance().logOut();
                                goLoginScreen();
                                break;


                        }
//                        Toast.makeText(CartActivity.this, "Item: " + i, Toast.LENGTH_SHORT).show();
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


        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Perfil").withIcon(activity.getResources().getDrawable(R.drawable.perfil)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Carrinho").withIcon(activity.getResources().getDrawable(R.drawable.carrinho)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Meus pedidos").withIcon(activity.getResources().getDrawable(R.drawable.pedidos)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Historico de itens").withIcon(activity.getResources().getDrawable(R.drawable.visualizacao)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Sair").withIcon(activity.getResources().getDrawable(R.drawable.sair)));
    }

    private void goLoginScreen() {
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    public void getProfile() {
        Profile profile = Profile.getCurrentProfile();
        if (getCurrentAccessToken() != null) {
            Log.d("Teste", AccessToken.getCurrentAccessToken().getUserId().toString());


            userId = AccessToken.getCurrentAccessToken().getUserId().toString();
            profileImgUrl = "https://graph.facebook.com/" + userId + "/picture?type=large";
            grafiUrl = "https://graph.facebook.com/me?access_token=" + AccessToken.getCurrentAccessToken().getToken();
            name = profile.getName();
            //   email =
            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {

                            Log.d("dqwdqqd", object.toString());
                            Log.d("dqwdqqd", grafiUrl);


                        }

                    });

            request.executeAsync();

            Log.d("xs", userId);


        }
    }


}







