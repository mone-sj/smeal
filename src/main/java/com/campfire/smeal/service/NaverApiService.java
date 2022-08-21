package com.campfire.smeal.service;

import com.campfire.smeal.dto.NaverSearchTrendShopping;
import com.campfire.smeal.dto.NaverTotalSearchTrend;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class NaverApiService {
/*
    // application.yml에 작성한 clientId/key 가 null로 받아옴. 추후 확인 필요
    @Value("${myinfo.naverApi.naverClientId}")
    private static String clientId;

    @Value("${myinfo.naverApi.naverClientSecret}")
    private static String clientSecret;
*/
    static final String clientId = "ne9fzrjuOdUV2oc8WaFh"; //애플리케이션 클라이언트 아이디값"
    static final String clientSecret = "RVyvkcBMPS"; //애플리케이션 클라이언트 시크릿값"


    // 블로그 검색 (재료에 따른 레시피 검색)\
    // https://developers.naver.com/docs/serviceapi/search/blog/blog.md#%EB%B8%94%EB%A1%9C%EA%B7%B8
    @Service
    public static class NaverSearchBlog {
        public String searchBlog(String searchWord) {
//            String clientId = "ne9fzrjuOdUV2oc8WaFh"; //애플리케이션 클라이언트 아이디값"
//            String clientSecret = "RVyvkcBMPS"; //애플리케이션 클라이언트 시크릿값"

            System.out.println("naver_ClientId:"+clientId);
            System.out.println("naver_clientkey:"+clientSecret);

            String text = null;
            try {
                text = URLEncoder.encode(searchWord, "UTF-8");
                System.out.println("검색어: "+text);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("검색어 인코딩 실패",e);
            }

            String searchBlog_apiURL =
                    "https://openapi.naver.com/v1/search/blog?query=" + text;    // json 결과

            Map<String, String> requestHeaders = new HashMap<>();
            requestHeaders.put("X-Naver-Client-Id", clientId);
            requestHeaders.put("X-Naver-Client-Secret", clientSecret);
            String responseBody = get(searchBlog_apiURL,requestHeaders);

            System.out.println(responseBody);

            return responseBody;

        }

        private static String get(
                String searchBlog_apiURL,
                Map<String, String> requestHeaders
        ){
            HttpURLConnection search_con = search_connect(searchBlog_apiURL);
            try {
                search_con.setRequestMethod("GET");
                for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                    search_con.setRequestProperty(header.getKey(), header.getValue());
                }


                int responseCode = search_con.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                    return search_readBody(search_con.getInputStream());
                } else { // 에러 발생
                    return search_readBody(search_con.getErrorStream());
                }
            } catch (IOException e) {
                throw new RuntimeException("API 요청과 응답 실패", e);
            } finally {
                search_con.disconnect();
            }
        }


        private static HttpURLConnection search_connect(
                String searchBlog_apiURL
        ){
            try {
                URL url = new URL(searchBlog_apiURL);
                return (HttpURLConnection)url.openConnection();
            } catch (MalformedURLException e) {
                throw new RuntimeException("API URL이 잘못되었습니다. : " + searchBlog_apiURL, e);
            } catch (IOException e) {
                throw new RuntimeException("연결이 실패했습니다. : " + searchBlog_apiURL, e);
            }
        }


        private static String search_readBody(InputStream body){
            InputStreamReader streamReader = new InputStreamReader(body);

            try (BufferedReader lineReader = new BufferedReader(streamReader)) {
                StringBuilder responseBody = new StringBuilder();

                String line;
                while ((line = lineReader.readLine()) != null) {
                    responseBody.append(line);
                }

                return responseBody.toString();
            } catch (IOException e) {
                throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
            }
        }

    }

    // 네이버 통합 검색어 트렌드 API
    // https://developers.naver.com/docs/serviceapi/datalab/search/search.md#%ED%86%B5%ED%95%A9-%EA%B2%80%EC%83%89%EC%96%B4-%ED%8A%B8%EB%A0%8C%EB%93%9C
    @Service
    public static class naverDatalabTotalSearchTrend {
        public String DatalabSearchTrend(NaverTotalSearchTrend searchTrendRequest) {
            String apiUrl = "https://openapi.naver.com/v1/datalab/search";

            Map<String, String> requestHeaders = new HashMap<>();
            requestHeaders.put("X-Naver-Client-Id", clientId);
            requestHeaders.put("X-Naver-Client-Secret", clientSecret);
            requestHeaders.put("Content-Type", "application/json");

            String requestBody = searchTrendRequest.toString();
            System.out.println("requestBody_String: "+requestBody);

//            String requestBody = "{\"startDate\":\"2017-01-01\"," +
//                    "\"endDate\":\"2017-04-30\"," +
//                    "\"timeUnit\":\"month\"," +
//                    "\"keywordGroups\":[{\"groupName\":\"한글\"," + "\"keywords\":[\"한글\",\"korean\"]}," +
//                    "{\"groupName\":\"영어\"," + "\"keywords\":[\"영어\",\"english\"]}]," +
//                    "\"device\":\"pc\"," +
//                    "\"ages\":[\"1\",\"2\"]," +
//                    "\"gender\":\"f\"}";

            String responseBody = post(apiUrl, requestHeaders, requestBody);
            System.out.println(responseBody);
            return responseBody;
        }

        private static String post(String apiUrl, Map<String, String> requestHeaders, String requestBody) {
            HttpURLConnection con = connect(apiUrl);

            try {
                con.setRequestMethod("POST");
                for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                    con.setRequestProperty(header.getKey(), header.getValue());
                }

                con.setDoOutput(true);
                try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                    wr.write(requestBody.getBytes());
                    wr.flush();
                }

                int responseCode = con.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 응답
                    return readBody(con.getInputStream());
                } else {  // 에러 응답
                    return readBody(con.getErrorStream());
                }
            } catch (IOException e) {
                throw new RuntimeException("API 요청과 응답 실패", e);
            } finally {
                con.disconnect(); // Connection을 재활용할 필요가 없는 프로세스일 경우
            }
        }

        private static HttpURLConnection connect(String apiUrl) {
            try {
                URL url = new URL(apiUrl);
                return (HttpURLConnection) url.openConnection();
            } catch (MalformedURLException e) {
                throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
            } catch (IOException e) {
                throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
            }
        }

        private static String readBody(InputStream body) {
            InputStreamReader streamReader = new InputStreamReader(body, StandardCharsets.UTF_8);

            try (BufferedReader lineReader = new BufferedReader(streamReader)) {
                StringBuilder responseBody = new StringBuilder();

                String line;
                while ((line = lineReader.readLine()) != null) {
                    responseBody.append(line);
                }

                return responseBody.toString();
            } catch (IOException e) {
                throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
            }
        }

    }

    // 네이버 쇼핑인사이트
    public String ApiDatalabTrendShopping(NaverSearchTrendShopping searchTrend) {
//        String clientId = "E7lq99dH_AYcqeMhX2YC"; // 애플리케이션 클라이언트 아이디
//        String clientSecret = "KsYRJgA0w_"; // 애플리케이션 클라이언트 시크릿

        String dataLabApiUrl = "https://openapi.naver.com/v1/datalab/shopping/categories";

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        requestHeaders.put("Content-Type", "application/json");

        String requestBody = String.valueOf(searchTrend);

        //String requestBody=searchTrend;

//        String requestBody = "{\"startDate\":\"2017-08-01\"," +
//                "\"endDate\":\"2017-09-30\"," +
//                "\"timeUnit\":\"month\"," +
//                "\"category\":[{\"name\":\"패션의류\",\"param\":[\"50000000\"]}," +
//                "{\"name\":\"화장품/미용\",\"param\":[\"50000002\"]}]," +
//                "\"device\":\"pc\"," +
//                "\"ages\":[\"20\",\"30\"]," +
//                "\"gender\":\"f\"}";

        String responseBody = dataLab_post(dataLabApiUrl, requestHeaders, requestBody);
        System.out.println(responseBody);
        return responseBody;

    }

    private static String dataLab_post(String dataLabApiUrl, Map<String, String> requestHeaders, String requestBody) {
        HttpURLConnection dataLab_con = dataLab_connect(dataLabApiUrl);

        try {
            dataLab_con.setRequestMethod("POST");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                dataLab_con.setRequestProperty(header.getKey(), header.getValue());
            }

            dataLab_con.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(dataLab_con.getOutputStream())) {
                wr.write(requestBody.getBytes());
                wr.flush();
            }

            int responseCode = dataLab_con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 응답
                return dataLab_readBody(dataLab_con.getInputStream());
            } else {  // 에러 응답
                return dataLab_readBody(dataLab_con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            dataLab_con.disconnect(); // Connection을 재활용할 필요가 없는 프로세스일 경우
        }
    }

    private static HttpURLConnection dataLab_connect(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String dataLab_readBody(InputStream body) {
        InputStreamReader streamReader = new InputStreamReader(body, StandardCharsets.UTF_8);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
}
