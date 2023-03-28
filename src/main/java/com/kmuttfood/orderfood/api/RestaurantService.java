package com.kmuttfood.orderfood.api;

import java.sql.Array;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kmuttfood.orderfood.entity.Menu;
import com.kmuttfood.orderfood.entity.Sidedish;
import com.kmuttfood.orderfood.entity.Size;

@Service
public class RestaurantService {

    @Autowired
    private SidedishRepository SidedishRepo;

    @Autowired
    private SizeRepository sizeRepo;

    @Autowired
    private MenuRepository menuRepo;

    @Autowired
    private OrderCustomerRepository orderCustomerRepo;

    public List<Sidedish> listAllSidedishs() {
        return SidedishRepo.findAll();
    }

    public Sidedish getSidedish(Integer id) {
        return SidedishRepo.findById(id).get();
    }

    public void saveSidedish(Sidedish sidedish) {
        SidedishRepo.save(sidedish);

    }

    public void deleteSidedish(Integer id) throws Exception {
        try {
            // Sidedish sidedish = SidedishRepo.findById(id).get();
            // orderCustomerRepo.deleteBySidedish(sidedish);
            SidedishRepo.deleteById(id);
        } catch (Exception e) {

        }

    }

    public List<Size> listAllSizes() {
        return sizeRepo.findAll();
    }

    public Size getSize(Integer id) {
        return sizeRepo.findById(id).get();
    }

    public void saveSize(Size size) {
        sizeRepo.save(size);

    }

    public void deleteSize(Integer id) throws Exception {
        try {
            // Size size = sizeRepo.findById(id).get();
            // orderCustomerRepo.deleteBySize(size);
            sizeRepo.deleteById(id);
        } catch (Exception e) {

        }
    }

    public List<Menu> listAllMenus() {
        return menuRepo.findAll();
    }

    public Menu getMenu(Integer id) {
        return menuRepo.findById(id).get();
    }

    public Menu saveMenu(Menu menu) {
        return menuRepo.save(menu);

    }

    public void deleteMenu(Integer id) throws Exception {
        // Menu menu = menuRepo.findById(id).get();
        // orderCustomerRepo.deleteByMenu(menu);

        try {
            menuRepo.deleteById(id);
        } catch (Exception e) {

        }
    }
}
