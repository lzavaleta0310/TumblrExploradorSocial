/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hightek.exploradorsocialtumblr_v1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import static com.google.gson.internal.bind.TypeAdapters.URL;
import com.hightek.exploradorsocialtumblr_v1.ui.UIPrincipal;
import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.Post;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 *
 * @author luis
 */
public class Main {

    public static void main(String[] args) throws IOException, MalformedURLException, JAXBException {
        
        UIPrincipal principal = new UIPrincipal();
        ControladorTumblr ct = new ControladorTumblr(principal);
        principal.getuIPantallaTumblr1().setControlador(ct);
        principal.setVisible(true);
        
    }

    
}
