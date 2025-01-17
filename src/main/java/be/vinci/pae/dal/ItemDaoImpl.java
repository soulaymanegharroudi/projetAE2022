package be.vinci.pae.dal;

import be.vinci.pae.business.domain.interfacesdto.DomainFactory;
import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.business.domain.interfacesdto.OfferDTO;
import be.vinci.pae.business.domain.interfacesdto.TypeDTO;
import be.vinci.pae.dal.interfaces.DalServices;
import be.vinci.pae.dal.interfaces.ItemDao;
import be.vinci.pae.dal.interfaces.MemberDao;
import be.vinci.pae.dal.interfaces.OfferDao;
import be.vinci.pae.exceptions.FatalException;
import jakarta.inject.Inject;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl implements ItemDao {

  @Inject
  DomainFactory domainFactory;
  @Inject
  DalServices services;
  @Inject
  MemberDao memberDao;
  @Inject
  OfferDao offerDao;

  public ItemDaoImpl() {
  }


  @Override
  public List<ItemDTO> getItemSortedBy(String sortingParam, String order) {
    boolean isCondition = false;
    if (sortingParam.equals("type") || sortingParam.equals("item_condition")) {
      sortingParam = "it." + sortingParam;
    } else {
      sortingParam = "of." + sortingParam;
    }
    String queryString;
    if (sortingParam.equals("it.item_condition")) {
      isCondition = true;
      queryString = "SELECT it.id_item,it.id_type,it.description,it.availabilities,"
          + "it.item_condition,it.photo,it.rating,it.id_offering_member,ty.type,of.id_offer "
          + "FROM pae.items it,pae.types ty,pae.offers of "
          + "WHERE it.id_type = ty.id_type AND of.id_item = it.id_item "
          + "AND it.item_condition = ?";
    } else {
      //language=PostgreSQL
      queryString = "SELECT it.id_item,it.id_type,it.description,it.availabilities,"
          + "it.item_condition,it.photo,it.rating,it.id_offering_member,ty.type,of.id_offer "
          + "FROM pae.items it,pae.types ty,pae.offers of "
          + "WHERE it.id_type = ty.id_type AND of.id_item = it.id_item " + "ORDER BY "
          + sortingParam + " " + order + " , id_item DESC";
    }

    try (PreparedStatement query = services.getPreparedStatement(queryString)) {
      if (isCondition) {
        query.setString(1, order);
      }
      return getItemFromDataBase(query);
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
  }

  @Override
  public ItemDTO getItem(int idItem) {
    List<ItemDTO> item = new ArrayList<>();
    try (PreparedStatement query = services.getPreparedStatement(
        "SELECT it.id_item,it.id_type,it.description,it.availabilities,"
            + "it.item_condition,it.photo,it.rating,it.id_offering_member,ty.type,of.date_offer "
            + "FROM pae.items it,pae.types ty,pae.offers of "
            + "WHERE it.id_type = ty.id_type AND of.id_item = it.id_item AND it.id_item = ? ")) {
      query.setInt(1, idItem);
      item = getItemFromDataBase(query);
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
    if (item.size() > 0) {
      return item.get(0);
    }
    return null;
  }


  @Override
  public int likeAnItem(int itemId, int idMember) {
    String now = LocalDate.now().toString();
    Date date = Date.valueOf(now);
    String query =
        "INSERT INTO pae.interests (id_item, id_member,date_delivery) VALUES (?,?,?)" + " RETURNING id_interest";
    try (PreparedStatement ps = services.getPreparedStatement(query)) {

      ps.setInt(1, itemId);
      ps.setInt(2, idMember);
      ps.setDate(3,date);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return rs.getInt(1);
        }
      }
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
    return -1;

  }

  @Override
  public int cancelAnOffer(int itemId) {
    String query = "UPDATE pae.items SET item_condition='cancelled' WHERE id_item=? RETURNING *";
    try (PreparedStatement ps = services.getPreparedStatement(query)) {
      ps.setInt(1, itemId);
      try (ResultSet rs = ps.executeQuery()) {
        return 1;
        //todo
      }
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
  }


  @Override
  public List<ItemDTO> getGivenItems() {
    List<ItemDTO> items;
    String tempQuery = "SELECT it.id_item,it.id_type,it.description,it.availabilities,"
        + "it.item_condition,it.photo,it.rating,it.id_offering_member,ty.type,of.date_offer "
        + "FROM pae.items it,pae.types ty,pae.offers of "
        + "WHERE it.id_type = ty.id_type AND of.id_item = it.id_item "
        + "AND it.item_condition = 'given' " + "ORDER BY date_offer DESC, id_item DESC";
    try (PreparedStatement query = services.getPreparedStatement(tempQuery)) {
      items = getItemFromDataBase(query);
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
    return items;
  }

  @Override
  public boolean offer(int idItem, int idOffer) {
    //language=PostgreSQL
    String query = "UPDATE pae.interests SET isrecipient=true WHERE id_item = ? AND id_member=?"
        + " RETURNING id_member ";
    try (PreparedStatement ps = services.getPreparedStatement(query)) {
      ps.setInt(1, idItem);
      ps.setInt(2, idOffer);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        return rs.getInt(1) > 0;
      }
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
    query = "UPDATE pae.items SET item_condition='assigned'  WHERE id_item= ?";
    try (PreparedStatement pss = services.getPreparedStatement(query)) {
      pss.setInt(1, idItem);
      pss.executeQuery();
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }

    return true;

  }

  //daoImpl


  @Override
  public ItemDTO createItem(ItemDTO newItem) {
    ItemDTO item = null;
    //language=PostgreSQL
    String query = "INSERT  INTO pae.items "
        + "(id_type,photo, description, availabilities, item_condition,id_offering_member) "
        + " VALUES(?,?,?,?,?,?) " + "RETURNING id_item,id_type,photo,description,availabilities,"
        + "item_condition,id_offering_member";
    try (PreparedStatement ps = services.getPreparedStatement(query)) {
      ps.setInt(1, newItem.getType().getIdType());
      ps.setString(2, newItem.getPhoto());
      ps.setString(3, newItem.getDescription());
      ps.setString(4, newItem.getAvailabilities());
      ps.setString(5, "published");
      ps.setInt(6, newItem.getOfferingMember().getIdMember());
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          item = createItemInstance(rs);
          return item;
        }
      }

    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }

    return item;

  }


  @Override
  public int typeExisting(String type) {
    //language=PostgreSQL
    String query = "SELECT id_type FROM pae.types WHERE type=? ";
    try (PreparedStatement ps = services.getPreparedStatement(query)) {
      ps.setString(1, type);

      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return rs.getInt(1);
        }
      }
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
    return -1;

  }


  @Override
  public int createType(String type) {
    //language=PostgreSQL
    String query = "INSERT INTO pae.types (type) VALUES (?) RETURNING id_type  ";
    try (PreparedStatement ps = services.getPreparedStatement(query)) {
      ps.setString(1, type);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return rs.getInt(1);
        }
      }
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
    return -1;

  }

  @Override
  public ItemDTO modify(int idItem, int type, String photo, String description,
      String availabilities) {

    ItemDTO item = null;
    String query = "";
    if (photo == null) {
      //language=PostgreSQL
      query =
          "UPDATE  pae.items SET  id_type=?,description= ?,availabilities= ? WHERE id_item=?"
              + "RETURNING id_item,id_type,photo,description,availabilities,"
              + "item_condition,id_offering_member";
    } else {
      //language=PostgreSQL
      query =
          "UPDATE  pae.items SET  id_type=?, description= ?,availabilities= ?, photo=?"
              + " WHERE id_item=?"
              + "RETURNING id_item,id_type,photo,description,availabilities,"
              + "item_condition,id_offering_member";
    }

    try (PreparedStatement ps = services.getPreparedStatement(query)) {
      ps.setInt(1, type);
      ps.setString(2, description);
      ps.setString(3, availabilities);
      if (photo != null) {
        ps.setString(4, photo);
        ps.setInt(5, idItem);
      } else {
        ps.setInt(4, idItem);
      }
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          item = createItemInstance(rs);
        }
      }

    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
    return item;
  }

  /**
   * Update the photo of the item, put the uuid file name.
   *
   * @param fileName name of the file (uuid + extension)
   * @param idItem   id of the item
   */
  @Override
  public void insertPhoto(String fileName, int idItem) {
    System.out.print("Passer par là : ItemDao");
    //language=PostgreSQL
    String query =
        "UPDATE  pae.items SET photo=? WHERE id_item=?"
            + "RETURNING id_item";

    try (PreparedStatement ps = services.getPreparedStatement(query)) {
      ps.setString(1, fileName);
      ps.setInt(2, idItem);
      ps.executeQuery();
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
  }


  @Override
  public List<ItemDTO> getOfferingMemberItems(int idMember) {
    String query =
        "SELECT it.*, ty.type, of.id_offer "
            + "FROM pae.items it, pae.members me, pae.types ty, pae.offers of "
            + "WHERE ty.id_type= it.id_type AND it.id_offering_member = me.id_member "
            + "AND of.id_item=it.id_item AND me.id_member = ?";
    try (PreparedStatement ps = services.getPreparedStatement(query)) {
      ps.setInt(1, idMember);
      return getItemFromDataBase(ps);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private List<ItemDTO> getItemFromDataBase(PreparedStatement query) throws SQLException {
    List<ItemDTO> items = new ArrayList<>();
    ResultSet resultSet = query.executeQuery();
    while (resultSet.next()) {
      ItemDTO item = domainFactory.getItem();
      TypeDTO type = domainFactory.getType();
      type.setIdType(resultSet.getInt("id_type"));
      type.setType(resultSet.getString("type"));
      item.setIdItem(resultSet.getInt("id_item"));
      item.setType(type);
      item.setDescription(resultSet.getString("description"));
      item.setAvailabilities(resultSet.getString("availabilities"));
      item.setItemCondition(resultSet.getString("item_condition"));
      item.setPhoto(resultSet.getString("photo"));
      item.setRating(resultSet.getInt("rating"));
      MemberDTO member = memberDao.getMember(resultSet.getInt("id_offering_member"));
      OfferDTO offer = offerDao.getOffer(resultSet.getInt("id_offer"));
      item.setOfferingMember(member);
      item.setOffer(offer);
      items.add(item);

    }
    resultSet.close();
    return items;
  }

  private ItemDTO createItemInstance(ResultSet rs) throws SQLException {
    ItemDTO item = domainFactory.getItem();
    TypeDTO type = domainFactory.getType();
    item.setIdItem(rs.getInt(1));
    type.setIdType(rs.getInt(2));
    item.setType(type);
    item.setPhoto(rs.getString(3));
    item.setDescription(rs.getString(4));
    item.setAvailabilities(rs.getString(5));
    item.setItemCondition("published");
    item.setOfferingMember(memberDao.getMember(8));
    rs.close();
    return item;
  }
}

