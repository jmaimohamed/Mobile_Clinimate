/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bibliotheque.gui;

import com.bibliotheque.Entite.Utilisateur;
import com.bibliotheque.Service.ServiceUtilisateur;
import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.table.TableLayout;
import java.util.ArrayList;

/**
 *
 * @author moham
 */
public class showForm extends BaseForm {

    ArrayList<Utilisateur> users;

    public showForm(Resources res) {
        users = ServiceUtilisateur.getInstance().getAllTasks();
        TableLayout tl = new TableLayout(users.size() + 1, 5);
        setLayout(tl);
        setTitle("List Tasks");
        super.addSideMenu(res);
        add(new Label("ID"));
        add(new Label("firstname"));
        add(new Label("lastname"));
        add(new Label("Delete"));
        add(new Label("Update"));
        for (Utilisateur t : users) {
            String sId = String.valueOf(t.getId());
            add(new Label(sId));
            add(new Label(t.getFirstname()));
            add(new Label(t.getLastname()));
            Button Delete = new Button("Delete");
            Button Update = new Button("Update");
            Delete.addActionListener(e -> {

                Dialog dig = new Dialog("Suppression");

                if (dig.show("Delete", "delete account ?", "Annuler", "Oui")) {
                    dig.dispose();
                } else {
                    dig.dispose();
                }
                if (ServiceUtilisateur.getInstance().delete(t.getId())) {
                    new showForm(res).show();
                }

            });
            add(Delete);
            
            Update.addActionListener(u ->{ System.out.println("hello update");
            new UpdateForm(res,t).show();});
            add(Update);
        }

    }
}
