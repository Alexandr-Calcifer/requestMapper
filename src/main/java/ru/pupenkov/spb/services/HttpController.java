package ru.pupenkov.spb.services;

import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.converter.json.GsonBuilderUtils;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class HttpController {

    public static HttpClient getHttpUrlConnectionWithProxy(String host, String port, String userName, String password) throws IOException {
        HttpHost proxy = new HttpHost(host, Integer.parseInt(port));
        Credentials credentials = new UsernamePasswordCredentials(userName, password);
        AuthScope authScope = new AuthScope(host, Integer.parseInt(port));
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(authScope, credentials);
        return HttpClientBuilder.create().setProxy(proxy).setDefaultCredentialsProvider(credsProvider).build();
    }

    public static HttpResponse getWithProxy(String domain, HttpClient client, String query) throws IOException {
        HttpGet httpGet = new HttpGet(domain + query);
        HttpResponse response = client.execute(httpGet);
        return response;
    }

    public static HttpResponse get(String domain, String query) throws IOException {
        HttpGet httpGet = new HttpGet(domain + query);
        HttpResponse response = HttpClientBuilder.create().build().execute(httpGet);
        return response;
    }

    public static String getResponseToString(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity, "UTF-8");
    }

    public static List<String> getValForEch(){
        List<String> list = new ArrayList<>();
        list.add("a1");
        list.add("a2");
        list.add("a3");
        List<String> list1 = list.stream().peek((p) -> p.concat("_new")).collect(Collectors.toList());
        System.out.println(list);
        System.out.println(list1);
        return list1;
    }

    private static boolean exicute(String a, Predicate<String> func){
        return func.test(a);
    }
    public static void main(String[] args) {
//        try {
//            HttpResponse httpResponse = HttpController.get(
//                    "http://pokrov.spb.ru"
//            , "/pacientu/priemnoe-otdelenie/");
//            System.out.println(getResponseToString(httpResponse));
//        } catch (IOException e) {
//            e.getMessage();
//        }
        String a = "Sasha";
        System.out.println(exicute(a, b -> b.equals("Sasha")));

        System.out.println(getValForEch());
    }
}
