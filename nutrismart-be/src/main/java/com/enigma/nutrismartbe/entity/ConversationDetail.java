package com.enigma.nutrismartbe.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "conversation_detail")
public class ConversationDetail {

    @Id
    @GeneratedValue(generator = "id_conversation_detail", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "id_conversation_detail", strategy = "uuid")
    private String id;
    private String replyText;
    private String userId;
    private Timestamp time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    @JsonIgnoreProperties(value = "conversationDetails")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id")
    @JsonIgnoreProperties(value = "conversationDetails")
    private Conversation conversation;

    public ConversationDetail() {
    }

    public ConversationDetail(String replyText, String userId, Timestamp time) {
        this.replyText = replyText;
        this.userId = userId;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReplyText() {
        return replyText;
    }

    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConversationDetail that = (ConversationDetail) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(replyText, that.replyText) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(time, that.time) &&
                Objects.equals(account, that.account) &&
                Objects.equals(conversation, that.conversation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, replyText, userId, time, account, conversation);
    }
}