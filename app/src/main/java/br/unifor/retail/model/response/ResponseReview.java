package br.unifor.retail.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.unifor.retail.model.Review;

/**
 * Created by vania on 27/10/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseReview implements Serializable{

     List<Review> reviews = new ArrayList<>();

    public List<Review> getReviews() {
        return reviews;
    }

    @Override
    public String toString() {
        return getReviews().toString();
    }
}

