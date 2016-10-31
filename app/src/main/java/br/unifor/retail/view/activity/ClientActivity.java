package br.unifor.retail.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
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
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import org.json.JSONObject;

import br.unifor.retail.R;

import static com.facebook.AccessToken.getCurrentAccessToken;

public class ClientActivity extends AppCompatActivity {
    private Drawer.Result navigationDrawerLeft;
    private AccountHeader.Result headerNavigationLeft;
    private Toolbar toolbar;
    private String userId;
    private String name;
    private String grafiUrl;
    private String profileImgUrl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        toolbar = (Toolbar) findViewById(R.id.toolbarClientt);
        toolbar.setTitle("Perfil");
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView nameView = (TextView) findViewById(R.id.cliente_nome);

        ImageView imageView = (ImageView) findViewById(R.id.profile_image);
        imageView.setImageResource(R.drawable.pandalambendo);

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
        createNavigationDrawer(savedInstanceState);
    }

    public void entrar_Minhas_Compras(View v){
        Intent intent = new Intent(this, My_ProductActivity.class);
        startActivity(intent);
    }

    public void entrar_Historico_Itens(View v){
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    public void entrar_Meu_Perfil(View v){
        Intent intent = new Intent(this, InfoClientActivity.class);
        startActivity(intent);
    }

    private void goLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void createNavigationDrawer(Bundle savedInstanceState) {
        //NAVIGATION DRAWER
        headerNavigationLeft = new AccountHeader()
                .withActivity(this)
                .withCompactStyle(false)
                .withSavedInstance(savedInstanceState)
                .withThreeSmallProfileImages(true)
                .withHeaderBackground(R.drawable.pandalambendo)
                .addProfiles(
                        new ProfileDrawerItem().withName(name).withEmail("vania.almeida28@hotmail.com").withIcon(profileImgUrl)

                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile iProfile, boolean b) {
                        Toast.makeText(ClientActivity.this, "onProfileChanged: " + iProfile.getName(), Toast.LENGTH_SHORT).show();
                        headerNavigationLeft.setBackgroundRes(R.drawable.camisa3);
                        return false;
                    }
                })
                .build();


        navigationDrawerLeft = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withDisplayBelowToolbar(false)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerGravity(Gravity.LEFT)
                .withSavedInstance(savedInstanceState)
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
                        switch (i){
                            case 0:
                                Intent intent = new Intent(ClientActivity.this, MainActivity_.class);
                                startActivity(intent);
                                break;
                            case 1:
                                Intent intent1 = new Intent(ClientActivity.this, CartActivity.class);
                                startActivity(intent1);
                                break;
                            case 2:
                                Intent intent2 = new Intent(ClientActivity.this, My_ProductActivity.class);
                                startActivity(intent2);
                                break;
                            case 3:
                                Intent intent3 = new Intent(ClientActivity.this, HistoryActivity.class);
                                startActivity(intent3);
                                break;
                            case 4:
                                LoginManager.getInstance().logOut();
                                goLoginScreen();
                                break;

                        }
                        Toast.makeText(ClientActivity.this, "Item: " + i, Toast.LENGTH_SHORT).show();
                    }
                })
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        Toast.makeText(ClientActivity.this, "onItemLongClick: " + i, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                .build();

        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Perfil").withIcon(getResources().getDrawable(R.mipmap.ic_launcher)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Carrinho").withIcon(getResources().getDrawable(R.mipmap.ic_launcher)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Meus pedidos").withIcon(getResources().getDrawable(R.mipmap.ic_launcher)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Historico de itens").withIcon(getResources().getDrawable(R.mipmap.ic_launcher)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Sair").withIcon(getResources().getDrawable(R.mipmap.ic_launcher)));
    }


}
