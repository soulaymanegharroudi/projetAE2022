package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import java.util.List;

public interface ItemUCC {

  List<ItemDTO> getLastOfferedItems();

  List<ItemDTO> getGivenItems();

  List<ItemDTO> getItemSortedBy(String sortingParam, String order);

  ItemDTO getItem(int idItem);

  int typeExisting(String type);


  int createType(String type);


  int likeAnItem(int offerId, int memberId);

  int cancelAnOffer(int itemId);

  ItemDTO createItem(ItemDTO item);
}
