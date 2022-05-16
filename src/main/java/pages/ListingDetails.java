package pages;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ListingDetails {

    private final String name;
    private final int price;
    private final double rating;

    public boolean equals(ListingDetails listingDetails) {
        if (listingDetails == null) {
            return false;
        }

        return listingDetails.getName().equalsIgnoreCase(this.getName()) &&
                listingDetails.getPrice() == this.getPrice() &&
                listingDetails.getRating() == this.getRating();
    }

    @Override
    public String toString() {
        return String.format(
                "[name = %s; price = %s; rating = %s]",
                this.name,
                this.price,
                this.rating
        );
    }
}
