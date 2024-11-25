package Modules;

import gamelogic.CurrentGame;
import gamelogic.Question;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {

    ReponseType response;
    public CurrentGame game;

    public List<Category> setOfCategories;
    Question question;

    public Response(ReponseType response) {
        this.response = response;
    }

    public Response(ReponseType response, CurrentGame game){
        this.response = response;
        this.game = game;
    }

    public Response(ReponseType response, List<Category> setOfCategories){
        this.response = response;
        this.setOfCategories = setOfCategories;
    }

    public Response(ReponseType response, Question question){
        this.response = response;
        this.question = question;
    }

    public ReponseType getType(){
        return this.response;
    }

    public List<Category> getSetOfCategories(){
        return setOfCategories;
    }

    public Question getQuestion(){
        return question;
    }
}
