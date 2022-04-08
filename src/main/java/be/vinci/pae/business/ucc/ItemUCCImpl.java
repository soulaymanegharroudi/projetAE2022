package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.dal.interfaces.DalServices;
import be.vinci.pae.dal.interfaces.ItemDao;
import be.vinci.pae.exceptions.BizExceptionForbidden;
import be.vinci.pae.exceptions.FatalException;
import jakarta.inject.Inject;
import java.util.List;

public class ItemUCCImpl implements ItemUCC {

  @Inject
  ItemDao itemDao;
  @Inject
  private DalServices dalServices;

  public ItemUCCImpl() {
  }

  /**
   * Get items from databased, sorted by the offer's date DESC.
   */
  @Override
  public List<ItemDTO> getLastOfferedItems() {
    try {
      dalServices.startTransaction();
      List<ItemDTO> list = itemDao.getLastOfferedItems();
      dalServices.commitTransaction();
      return list;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new FatalException(e.getMessage());
    }
  }

  @Override
  public ItemDTO getItem(int idItem) {
    try {
      dalServices.startTransaction();
      if (idItem < 1) {
        throw new BizExceptionForbidden("L'id de l'objet doit être supérieur à 0.");
      }
      ItemDTO item = itemDao.getItem(idItem);
      if (item == null) {
        throw new BizExceptionForbidden("L'objet désiré n'existe pas.");
      }
      dalServices.commitTransaction();
      return item;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new FatalException(e.getMessage());
    }
  }


  @Override
  public List<ItemDTO> getGivenItems() {
    try {
      dalServices.startTransaction();
      List<ItemDTO> items = itemDao.getGivenItems();
      dalServices.commitTransaction();
      return items;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new FatalException(e.getMessage());
    }
  }

  /**
   * like an offer by its id.
   *
   * @return number of interests on this offer.
   * @params offerId and memberId
   */
  @Override
  public int likeAnItem(int offerId, int memberId) {
    try {
      dalServices.startTransaction();
      int interests = itemDao.likeAnItem(offerId, memberId);
      dalServices.commitTransaction();
      return interests;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      e.printStackTrace();
    }
    return -1;
  }


  /**
   * Cancel an offer.
   *
   * @return 1 if ok.
   * @params itemId
   */
  @Override
  public int cancelAnOffer(int itemId) {
    try {
      dalServices.startTransaction();
      int returned = itemDao.cancelAnOffer(itemId);
      dalServices.commitTransaction();
      return returned;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      e.printStackTrace();
    }
    return -1;
  }


  @Override
  public ItemDTO createItem(ItemDTO item) {
    try {
      dalServices.startTransaction();
      ItemDTO itemDTO = itemDao.createItem(item);
      dalServices.commitTransaction();
      return itemDTO;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public int typeExisting(String type) {
    try {
      dalServices.startTransaction();
      int typeId = itemDao.typeExisting(type);
      dalServices.commitTransaction();
      return typeId;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      e.printStackTrace();
    }
    return -1;
  }

  @Override
  public int createType(String type) {
    try {
      dalServices.startTransaction();
      int typeId = itemDao.createType(type);
      dalServices.commitTransaction();
      return typeId;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      e.printStackTrace();
    }
    return -1;
  }

  @Override
  public boolean isLiked(int idItem) {
    try {
      dalServices.startTransaction();
      if (idItem < 1) {
        throw new BizExceptionForbidden("L'id de l'objet doit être supérieur à 0.");
      }
      boolean isLiked = itemDao.isLiked(idItem);
      dalServices.commitTransaction();
      return isLiked;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new FatalException(e.getMessage());
    }
  }
}


