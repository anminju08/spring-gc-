package gc.board.article.api;

import gc.board.article.service.response.ArticleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

public class ArticleApiTest {
    RestClient restClient = RestClient.create("http://localhost:9000");

    @Test
    void createTest(){
        ArticleResponse response = create(new ArticleCreateRequest(
                "hi", "my content", 1L, 1L
        ));
        System.out.println("response = "+response);
    }

    ArticleResponse create(ArticleCreateRequest request){
        return restClient.post()
                .uri("/v1/articles")
                .body(request)
                .retrieve()
                .body(ArticleResponse.class);
    }
    
    //생성된 article_id: 229196326023962624
    
    @Test
    void readTest(){
     ArticleResponse response = read(229196326023962624L);
        System.out.println("response = " + response);
    }
    
    ArticleResponse read(Long articleId){
        return restClient.get()
                .uri("/v1/articles/{articleId}", articleId)
                .retrieve()
                .body(ArticleResponse.class);
    }


    //수정테스트코드
    @Test
    void updateTest(){
        ArticleResponse response = update(
                229196326023962624L, new ArticleUpdateRequest(
                        "hi2", "my content 2")
        );
        System.out.println("response = " + response);
    }

    ArticleResponse update(Long articleId, ArticleUpdateRequest request){
        return restClient.put()
                .uri("/v1/articles/{articleId}", articleId)
                .body(request)
                .retrieve()
                .body(ArticleResponse.class);
    }

    //삭제테스트코드
    @Test
    void deleteTest(){
        restClient.delete()
                .uri("/v1/articles/{articleId}",229196326023962624L)
                .retrieve();
    }

    @Getter
    @ToString
    @AllArgsConstructor
    static class ArticleCreateRequest {
        private String title;
        private String content;
        private Long boardId;
        private Long writerId;
    }

    @Getter
    @ToString
    @AllArgsConstructor
    static class ArticleUpdateRequest {
        private String title;
        private String content;
    }
}

