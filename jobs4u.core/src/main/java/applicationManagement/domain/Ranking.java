package applicationManagement.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Embeddable
public class Ranking {

    private String rank;

    public Ranking() {
        this.rank = "Not Ranked";
    }

//    @Override
//    public int compareTo(Integer o) {
//        if (this.ordinal < o) {
//            return -1;
//        } else if (this.ordinal > o) {
//            return 1;
//        }
//        return 0;
//    }
}
