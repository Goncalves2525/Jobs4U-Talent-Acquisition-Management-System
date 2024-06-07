package applicationManagement.domain;

import eapli.framework.domain.model.ValueObject;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;


@Setter
@Embeddable
public class Ranking implements ValueObject, Comparable<Integer> {

    @Getter
    private int ordinal;

    public Ranking() {
    }

    @Override
    public int compareTo(Integer o) {
        if (this.ordinal < o) {
            return -1;
        } else if (this.ordinal > o) {
            return 1;
        }
        return 0;
    }
}
