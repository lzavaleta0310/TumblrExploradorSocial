/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hightek.exploradorsocialtumblr_v1;

import com.google.gson.Gson;
import com.hightek.exploradorsocialtumblr_v1.ui.UIPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author luis
 */
public class ControladorTumblr {

    private UIPrincipal uiPrincipal;
    private List<Datos.Response> listaDatos;

    public ControladorTumblr(UIPrincipal uiPrincipal) {
        listaDatos = new ArrayList<Datos.Response>();
        this.uiPrincipal = uiPrincipal;
    }

    public void llenarDatos(Datos.Response post) {
        this.uiPrincipal.getuIPantallaTumblr1().getjTable1().removeAll();
        DefaultTableModel modelo = (DefaultTableModel) this.uiPrincipal.getuIPantallaTumblr1().getjTable1().getModel();
        Object[] fila = new Object[4];
        fila[0] = post.getId();
        fila[1] = post.getType();
        fila[2] = post.getPost_url();
        fila[3] = post.getDate();
        modelo.addRow(fila);
        this.uiPrincipal.getuIPantallaTumblr1().getjTable1().setModel(modelo);
    }

    public void llenarDatos(List<Datos.Response> posts) {
        DefaultTableModel modelo = (DefaultTableModel) this.uiPrincipal.getuIPantallaTumblr1().getjTable1().getModel();
        modelo.getDataVector().removeAllElements();

        for (Datos.Response post : posts) {
            Object[] fila = new Object[4];
            fila[0] = post.getId();
            fila[1] = post.getType();
            fila[2] = post.getPost_url();
            fila[3] = post.getDate().split(" ")[0] + " " + post.getDate().split(" ")[1];
            modelo.addRow(fila);
            this.uiPrincipal.getuIPantallaTumblr1().getjTable1().setModel(modelo);
        }
    }

    public void obtenerDatosTumblr(String tag) {
        javax.swing.Timer timer = new Timer(6000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String respuesta = getJSON("https://api.tumblr.com/v2/tagged?tag=" + tag + "&api_key=fuiKNFp9vQFvjLNvx4sUwti4Yb5yGutBN4Xh10LXZhhRKjWlV4", 500);
                Gson gson = new Gson();
                Datos d = gson.fromJson(respuesta, Datos.class);

                if (d != null) {
                    List<Datos.Response> lTemp = new ArrayList<Datos.Response>();
                    lTemp = d.getResponse();
                    filtrarDatos(lTemp);
                }
            }
        });

        timer.start();
    }

    private void filtrarDatos(List<Datos.Response> ld) {
        if (this.listaDatos.isEmpty()) {
            this.listaDatos = ld;
            llenarDatos(this.listaDatos);
        } else {
            if(ld.get(0).getId() != this.listaDatos.get(0).getId()){
                for(int i = 0; i < ld.size(); i++){
                    if(!searchPost(ld.get(i))){
                        this.listaDatos.add(0, ld.get(i));
                    }
                }
                llenarDatos(this.listaDatos);
            }
        }
    }
    
    private boolean searchPost(Datos.Response post){
        for(int i = 0; i < listaDatos.size(); i++){
            if(listaDatos.get(i).getId() == post.getId()){
                return true;
            }
        }
        return false;
    }

    public static String getJSON(String url, int timeout) {
        HttpURLConnection c = null;
        try {
            URL u = new URL(url);
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(timeout);
            c.setReadTimeout(timeout);
            c.connect();
            int status = c.getResponseCode();

            switch (status) {
                case 201:
                case 200:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    return sb.toString();
            }

        } catch (MalformedURLException ex) {
            //Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            //Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (c != null) {
                try {
                    c.disconnect();
                } catch (Exception ex) {
                    //Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

}
