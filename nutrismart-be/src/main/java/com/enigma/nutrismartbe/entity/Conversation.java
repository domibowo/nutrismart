package com.enigma.nutrismartbe.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "mst_conversation")
public class Conversation {

    @Id
    @GeneratedValue(generator = "id_conversation", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "id_conversation", strategy = "uuid")
    private String id;
    private String userOne;
    private String userTwo;
    private Timestamp time;
    private String conversationCol;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conversation")
    @JsonIgnoreProperties(value = {"conversation"})
    private List<ConversationDetail> conversationDetails;


    public Conversation() {
    }

    public Conversation(String userOne, String userTwo, Timestamp time, String conversationCol) {
        this.userOne = userOne;
        this.userTwo = userTwo;
        this.time = time;
        this.conversationCol = conversationCol;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserOne() {
        return userOne;
    }

    public void setUserOne(String userOne) {
        this.userOne = userOne;
    }

    public String getUserTwo() {
        return userTwo;
    }

    public void setUserTwo(String userTwo) {
        this.userTwo = userTwo;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getConversationCol() {
        return conversationCol;
    }

    public void setConversationCol(String conversationCol) {
        this.conversationCol = conversationCol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conversation that = (Conversation) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userOne, that.userOne) &&
                Objects.equals(userTwo, that.userTwo) &&
                Objects.equals(time, that.time) &&
                Objects.equals(conversationCol, that.conversationCol) &&
                Objects.equals(conversationDetails, that.conversationDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userOne, userTwo, time, conversationCol, conversationDetails);
    }
}