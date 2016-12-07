package team.software.connection;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetDataCards {
    @SerializedName("cards") private List<Cards> cards = null;
    @SerializedName("default") private CardDefault defaul_id;

    public List<Cards> getCard() {
        return cards;
    }

    public void setCard(List<Cards> card) {
        this.cards = card;
    }

    public CardDefault getDefaul_id() {
        return defaul_id;
    }

    public void setDefaul_id(CardDefault defaul_id) {
        this.defaul_id = defaul_id;
    }
}
