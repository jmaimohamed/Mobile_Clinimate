/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bibliotheque.Service;

import com.bibliotheque.gui.SessionManager;
import com.bibliotheque.utils.Statics;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.util.Resources;
import com.bibliotheque.gui.SessionManager;
import com.bibliotheque.utils.Statics;
import java.util.Map;

/**
 *
 * @author moham
 */
public class ServiceUtilisateur {

    //singleton 
    public static ServiceUtilisateur instance = null;

    public static boolean resultOk = true;
    String json;

    //initilisation connection request 
    private ConnectionRequest req;

    public static ServiceUtilisateur getInstance() {
        if (instance == null) {
            instance = new ServiceUtilisateur();
        }
        return instance;
    }

    public ServiceUtilisateur() {
        req = new ConnectionRequest();

    }

    //Signup
    public void signup(TextField firstname, ComboBox<String> roles, TextField lastname, TextField password, TextField email, TextField address, TextField telephone, Resources res) {

        String url = Statics.BASE_URL + "/User/signup?email=" +email.getText().toString() 
                + "&roles" + roles.getSelectedItem().toString()
                + "&password=" + password.getText().toString()
                + "&firstname=" + firstname.getText().toString()
                + "&lastname=" + lastname.getText().toString() 
                + "&address=" + address.getText().toString()
                +"&telephone="+ telephone.getText().toString() ;
        
        

        req.setUrl(url);

        //Control saisi
        if (firstname.getText().equals(" ") && lastname.getText().equals(" ") &&address.getText().equals(" ") &&password.getText().equals(" ") && telephone.getText().equals(" ") && email.getText().equals(" ")) {

            Dialog.show("Erreur", "Veuillez remplir les champs", "OK", null);

        }

        //hethi wa9t tsir execution ta3 url 
        req.addResponseListener((e) -> {

            //njib data ly7atithom fi form 
            byte[] data = (byte[]) e.getMetaData();//lazm awl 7aja n7athrhom ke meta data ya3ni na5o id ta3 kol textField 
            String responseData = new String(data);//ba3dika na5o content 

            System.out.println("data ===>" + responseData);
        }
        );

        //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
        NetworkManager.getInstance().addToQueueAndWait(req);

    }

    //SignIn
    public void signin(TextField email, TextField password, Resources rs) {

        String url = Statics.BASE_URL + "/User/signin?email=" + email.getText().toString() + "&password=" + password.getText().toString();
        req = new ConnectionRequest(url, false); //false ya3ni url mazlt matba3thtich lel server
        req.setUrl(url);

        req.addResponseListener((e) -> {

            JSONParser j = new JSONParser();

            String json = new String(req.getResponseData()) + "";
            try {
                if (json.equals("failed")) {
                    Dialog.show("Echec d'authentification", "Username ou mot de passe éronné", "OK", null);
                } else {
                    System.out.println("data ==" + json);

                    Map<String, Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));

                    //Session 
                    float id = Float.parseFloat(user.get("id").toString());
                    SessionManager.setId((int) id);//jibt id ta3 user ly3ml login w sajltha fi session ta3i

                    SessionManager.setPassowrd(user.get("password").toString());
                    SessionManager.setEmail(user.get("email").toString());
                }}catch(Exception ex) {
                    ex.printStackTrace();
                }});

                //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
                NetworkManager.getInstance().addToQueueAndWait(req);
            }

        }
