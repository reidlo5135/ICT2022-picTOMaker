package kr.co.picto.common.application;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommonRequestService {
    private final RestTemplate restTemplate;
    private final Gson gson;

    public Map<String, String> get(String url) {
        return gson.fromJson(restTemplate.getForEntity(url, String.class).getBody(), Map.class);
    }

    public Map<String, String> post(String url, HttpEntity httpEntity) {
        return gson.fromJson(restTemplate.postForEntity(url, httpEntity, String.class).getBody(), Map.class);
    }
}
