package com.kmuttfood.orderfood.api;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.AuthProvider;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

import javax.activation.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kmuttfood.orderfood.entity.Menu;
import com.kmuttfood.orderfood.entity.OrderCustomer;
import com.kmuttfood.orderfood.entity.Role;
import com.kmuttfood.orderfood.entity.Sidedish;
import com.kmuttfood.orderfood.entity.Size;
import com.kmuttfood.orderfood.entity.Status;
import com.kmuttfood.orderfood.entity.User;

@Controller
public class App {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderSevice orderSevice;

    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }

    @GetMapping("/admin/login")
    public String viewLoginAdminPage(Model model) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "loginAdmin";
        }

        return "loginAdmin";
    }

    @GetMapping("/user/login")
    public String viewLoginUserPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "Login";
        }

        return "Login";
    }

    @GetMapping("/admin/logout")
    public String viewLogoutAdminPage() {
        return "loginAdmin";
    }

    @GetMapping("/user/logout")
    public String viewLogoutUesrPage() {
        return "Login";
    }

    @GetMapping("/register")
    public String viewRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "sighup";
    }

    @PostMapping("/process_register")
    public String processRegisteration(User user, Model model) {
        if (userService.creat(user) == false) {
            model.addAttribute("message2", "This email has already been registered.");
            return "sighup";
        }

        return "redirect:/user/login";

    }

    @GetMapping("/admin/welcome")
    String viewWelcomeAdminPage() {
        return "welcome";
    }

    @GetMapping("/user/welcome")
    String viewWelcomeUserPage() {
        return "welcomeUser";
    }

    @GetMapping("/admin/list_users")
    public String viewUsersList(Model model) {
        List<User> listUsers = userService.findListUser(true);
        model.addAttribute("listUsers", listUsers);
        return "users";
    }

    @GetMapping("/admin/list_users/ban")
    public String viewListOfBannedUser(Model model) {
        List<User> listUsers = userService.findListUser(false);
        model.addAttribute("listUsers", listUsers);
        return "usersBan";
    }

    @GetMapping("/admin/list_users/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = userService.getId(id);
        List<Role> listRoles = userService.listRoles();
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        return "user_form";
    }

    @RequestMapping("/admin/list_users/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Long id) throws Exception {
        userService.delete(id);
        return "redirect:/admin/list_users";
    }

    @PostMapping("/admin/list_users/save")
    public String saveUser(User user) {
        userService.save(user);
        return "redirect:/admin/list_users";
    }

    @GetMapping("/admin/restaurant")
    public String viewRestaurantPage() {
        return "Restaurant";
    }

    @GetMapping("/admin/sidedish")
    public String viewSidedishPage(Model model) {
        List<Sidedish> listSidedish = restaurantService.listAllSidedishs();
        model.addAttribute("listSidedish", listSidedish);
        return "Sidedish";
    }

    @GetMapping("/admin/add_sidedish")
    public String viewAddSidedishPage(Model model) {
        model.addAttribute("sidedish", new Sidedish());
        return "AddSidedish";
    }

    @PostMapping("/admin/process_sidedish")
    public String processAddSidedish(Sidedish sidedish, Model model) {
        restaurantService.saveSidedish(sidedish);
        return "redirect:/admin/sidedish";
    }

    @GetMapping("/admin/process_sidedish/edit/{id}")
    public String editSidedish(@PathVariable("id") Integer id, Model model) {
        Sidedish sidedish = restaurantService.getSidedish(id);
        model.addAttribute("sidedish", sidedish);
        return "Sidedish_edit";
    }

    @RequestMapping("/admin/process_sidedish/delete/{id}")
    public String deleteSidedish(@PathVariable(name = "id") Integer id) throws Exception {
        restaurantService.deleteSidedish(id);
        return "redirect:/admin/sidedish";
    }

    @PostMapping("/admin/process_sidedish/save")
    public String saveSidedish(Sidedish sidedish) {
        restaurantService.saveSidedish(sidedish);
        return "redirect:/admin/sidedish";
    }

    @GetMapping("/admin/size")
    public String viewSizePage(Model model) {
        List<Size> listSize = restaurantService.listAllSizes();
        model.addAttribute("listSize", listSize);
        return "Size";
    }

    @GetMapping("/admin/add_size")
    public String viewAddSizePage(Model model) {
        model.addAttribute("size", new Size());
        return "AddSize";
    }

    @PostMapping("/admin/process_size")
    public String processAddSidedish(Size size, Model model) {
        restaurantService.saveSize(size);
        return "redirect:/admin/size";
    }

    @GetMapping("/admin/process_size/edit/{id}")
    public String editSize(@PathVariable("id") Integer id, Model model) {
        Size size = restaurantService.getSize(id);
        model.addAttribute("size", size);
        return "Size_edit";
    }

    @RequestMapping("/admin/process_size/delete/{id}")
    public String deleteSize(@PathVariable(name = "id") Integer id) throws Exception {
        restaurantService.deleteSize(id);
        return "redirect:/admin/size";
    }

    @PostMapping("/admin/process_size/save")
    public String saveSize(Size size) {
        restaurantService.saveSize(size);
        return "redirect:/admin/size";
    }

    @GetMapping("/admin/menu")
    public String viewMenuPage(Model model) {
        List<Menu> listMenu = restaurantService.listAllMenus();
        model.addAttribute("listMenu", listMenu);
        return "Menu";
    }

    @GetMapping("/admin/add_menu")
    public String viewAddMenuPage(Model model) {
        model.addAttribute("menu", new Menu());
        return "AddMenu";
    }

    @PostMapping("/admin/process_menu/save")
    public String saveMenu(Menu menu, @RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        menu.setImage(filename);

        Menu savedmenu = restaurantService.saveMenu(menu);
        String uploadDir = "/menu-image/" + savedmenu.getId();
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(filename);
            System.out.println(filePath.toString());
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Could not save uploaded file: " + filename);
        }
        return "redirect:/admin/menu";
    }

    @GetMapping("/admin/process_menu/edit/{id}")
    public String editMenu(@PathVariable("id") Integer id, Model model) {
        Menu menu = restaurantService.getMenu(id);
        model.addAttribute("menu", menu);
        return "Menu_edit";
    }

    @RequestMapping("/admin/process_menu/delete/{id}")
    public String deleteMenu(@PathVariable(name = "id") Integer id) throws Exception {
        restaurantService.deleteMenu(id);
        return "redirect:/admin/menu";
    }

    @GetMapping("/user/menu_user")
    public String ViewMenuOfUserPage(User user, Model model) {

        List<Menu> listMenu = restaurantService.listAllMenus();
        model.addAttribute("listMenu", listMenu);

        return "MenuUser";
    }

    @GetMapping("/user/process_order/{id}")
    public String viewOrderPage(@PathVariable("id") Integer id, Model model,
            @AuthenticationPrincipal CutomerUserDetails userDetails) {
        Menu menu = restaurantService.getMenu(id);
        List<Size> listSize = restaurantService.listAllSizes();
        List<Sidedish> listSidedish = restaurantService.listAllSidedishs();
        model.addAttribute("listSidedish", listSidedish);
        model.addAttribute("listSize", listSize);
        model.addAttribute("OrderCustomer", new OrderCustomer());
        model.addAttribute("menuid", menu.getId());
        model.addAttribute("listmenu", menu);

        return "Order";
    }

    @GetMapping("/user/process_order/save/{id}")
    public String saveOrder(@PathVariable("id") Integer id, @AuthenticationPrincipal CutomerUserDetails userDetails,
            OrderCustomer orderCustomer, Model model) throws Exception {
        if (orderCustomer.getQuatity() == 0) {
            orderSevice.deleteOrderCustomer(orderCustomer.getId());
        }

        String userEmail = userDetails.getUsername();
        User user = userService.getEmail(userEmail);
        Status status = orderSevice.getsIDStatus("เพิ่มใส่ตะกร้า");
        Menu menu = restaurantService.getMenu(id);
        orderSevice.createOrder(user, status, orderCustomer, menu);
        return "redirect:/user/menu_user";
    }

    @GetMapping("/user/order/edit/{id}")
    public String editOrder(@PathVariable("id") Integer id, Model model) {
        OrderCustomer orderCustomer = orderSevice.getOrderCustomer(id);
        Menu menu = orderCustomer.getMemu();
        List<Size> listSize = restaurantService.listAllSizes();
        List<Sidedish> listSidedish = restaurantService.listAllSidedishs();
        model.addAttribute("listmenu", menu);
        model.addAttribute("menuid", menu.getId());
        model.addAttribute("listSidedish", listSidedish);
        model.addAttribute("listSize", listSize);
        model.addAttribute("orderCustomer", orderCustomer);
        return "Edit_Order";
    }

    @RequestMapping("/user/order/delete/{id}")
    public String deleteOrder(@PathVariable(name = "id") Integer id) {
        orderSevice.deleteOrderCustomer(id);
        return "redirect:/user/cart";
    }

    @GetMapping("/user/cart")
    public String viewCartOfUser(Model model, @AuthenticationPrincipal CutomerUserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        User user = userService.getEmail(userEmail);
        Status status = orderSevice.getsIDStatus("เพิ่มใส่ตะกร้า");
        List<OrderCustomer> listOrderCustomer = orderSevice.findOrderofUserByStaus(status, user);
        model.addAttribute("listOrderCustomer", listOrderCustomer);
        return "CartOfUser";
    }

    @GetMapping("/user/liststatusOrder")
    public String viewSubmitOrderOfUser(Model model, @AuthenticationPrincipal CutomerUserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        User user = userService.getEmail(userEmail);
        Status status = orderSevice.getsIDStatus("เพิ่มใส่ตะกร้า");
        Date dateToday = new Date(System.currentTimeMillis());
        List<OrderCustomer> listOrderCustomer = orderSevice.findOrderofUserByNotisStaus(status, user, dateToday);
        model.addAttribute("listOrderCustomer", listOrderCustomer);
        return "OrderStatusOfUser";
    }

    @GetMapping("/user/order_to_rastaurant")
    public String processOrderToRastaurant(
            @AuthenticationPrincipal CutomerUserDetails userDetails, Model model) {
        String userEmail = userDetails.getUsername();
        User user = userService.getEmail(userEmail);
        Date date = new Date(System.currentTimeMillis());
        Time time = new Time(System.currentTimeMillis());
        Status status = orderSevice.getsIDStatus("รอรับออเดอร์");
        Status status2 = orderSevice.getsIDStatus("เพิ่มใส่ตะกร้า");
        orderSevice.updateTimelistOrders(time, status2, user);
        orderSevice.updateDatelistOrders(date, status2, user);
        orderSevice.updateStatuslistOrders(status, status2);
        List<OrderCustomer> listOrderCustomer = orderSevice.listOrderCustomer(user);
        model.addAttribute("listOrderCustomer", listOrderCustomer);
        return "redirect:/user/liststatusOrder";
    }

    @GetMapping("/admin/order_today/neworeder")
    public String viewOrderStatusNewOrder(Model model) {
        Status status = orderSevice.getsIDStatus("รอรับออเดอร์");
        Date dateToday = new Date(System.currentTimeMillis());
        List<OrderCustomer> listOrderCustomer = orderSevice.findlistOrderByStausToday(status, dateToday);
        model.addAttribute("listOrderCustomer", listOrderCustomer);
        return "OrderStatusNewOreder";
    }

    @GetMapping("/admin/updateStatus/takeorder/{id}")
    public String processUpdateStatusToTakeOreder(@PathVariable("id") Integer id, Model model) {
        Status status = orderSevice.getsIDStatus("รับออเดอร์แล้ว");
        orderSevice.updateStatus(status, id);
        return "redirect:/admin/order_today/neworeder";
    }

    @GetMapping("/admin/order_today/takeorder")
    public String viewOrderStatusTakeOrder(Model model) {
        Status status = orderSevice.getsIDStatus("รับออเดอร์แล้ว");
        Date dateToday = new Date(System.currentTimeMillis());
        List<OrderCustomer> listOrderCustomer = orderSevice.findlistOrderByStausToday(status, dateToday);
        model.addAttribute("listOrderCustomer", listOrderCustomer);
        return "OrderStatusTakeOrder";
    }

    @GetMapping("/admin/updateStatus/finishedcooking/{id}")
    public String processUpdateStatusTofinishedcooking(@PathVariable("id") Integer id, Model model) {
        Status status = orderSevice.getsIDStatus("อาหารเสร็จแล้ว");
        orderSevice.updateStatus(status, id);
        return "redirect:/admin/order_today/takeorder";
    }

    @GetMapping("/admin/order_today/finishedcooking")
    public String viewOrderStatusFinishedCooking(Model model) {
        Status status = orderSevice.getsIDStatus("อาหารเสร็จแล้ว");
        Date dateToday = new Date(System.currentTimeMillis());
        List<OrderCustomer> listOrderCustomer = orderSevice.findlistOrderByStausToday(status, dateToday);
        model.addAttribute("listOrderCustomer", listOrderCustomer);
        return "OrderStatusFinishedCooking";
    }

    @GetMapping("/admin/updateStatus/paymentcompleted/{id}")
    public String processUpdateStatusToPaymentCompleted(@PathVariable("id") Integer id, Model model) {
        Status status = orderSevice.getsIDStatus("ชำระเงินแล้ว");
        orderSevice.updateStatus(status, id);
        return "redirect:/admin/order_today/finishedcooking";
    }

    @GetMapping("/admin/order_today/paymentcompleted")
    public String viewOrderStatusPaymentCompleted(Model model) {
        Status status = orderSevice.getsIDStatus("ชำระเงินแล้ว");
        Date dateToday = new Date(System.currentTimeMillis());
        List<OrderCustomer> listOrderCustomer = orderSevice.findlistOrderByStausToday(status, dateToday);
        model.addAttribute("listOrderCustomer", listOrderCustomer);
        return "OrderStstusPaymentCompleted";
    }

    @GetMapping("/admin/updateStatus/nopayment/{id}")
    public String processUpdateStatusToNoPayment(@PathVariable("id") Integer id, Model model) {
        Status status = orderSevice.getsIDStatus("ไม่ชำระเงิน");
        orderSevice.updateStatus(status, id);
        return "redirect:/admin/order_today/finishedcooking";
    }

    @GetMapping("/admin/order_today/nopayment")
    public String viewOrderStatusNoPayment(Model model) {
        Status status = orderSevice.getsIDStatus("ไม่ชำระเงิน");
        Date dateToday = new Date(System.currentTimeMillis());
        List<OrderCustomer> listOrderCustomer = orderSevice.findlistOrderByStausToday(status, dateToday);
        model.addAttribute("listOrderCustomer", listOrderCustomer);
        return "OrderNoPayment";
    }

    @GetMapping("/admin/detailUser/order/{id}")
    public String viewDetailOrder(@PathVariable("id") Integer id, Model model) {
        OrderCustomer orderCustomer = orderSevice.getOrderCustomer(id);
        Menu menu = orderCustomer.getMemu();
        Sidedish sidedish = orderCustomer.getSidedish();
        Size size = orderCustomer.getSize();
        User user = orderCustomer.getUser();
        model.addAttribute("listOrderCustomer", orderCustomer);
        model.addAttribute("pricemenu", menu.getPrice());
        model.addAttribute("pricesize", size.getPrice());
        model.addAttribute("pricesidedish", sidedish.getPrice());
        model.addAttribute("userid", user.getId());
        model.addAttribute("userPhone", user.getPhone_number());
        model.addAttribute("Userfullname", user.getFirstname() + " " + user.getLastname());

        return "DetailOrderUser";
    }

    @GetMapping("/admin/sales/hidtory")
    public String viewSalesHistoryPage() {
        return "SalesHistory";
    }

    @RequestMapping("/admin/sales/hidtory/process")
    public String viewSalesHistoryByDatePage(@RequestParam("date") Date date, Model model) {
        model.addAttribute("date", date);
        Status status = orderSevice.getsIDStatus("ชำระเงินแล้ว");
        List<OrderCustomer> listOrderCustomer = orderSevice.findlistOrderComplect(date, status);
        Integer total = orderSevice.findTotal(date, status);
        model.addAttribute("total", total);
        model.addAttribute("listOrderCustomer", listOrderCustomer);
        return "SalesHistoryofDate";

    }
    /*
     * @GetMapping("/admin/ban/{id}")
     * public String processBanAccountByIdUser(@PathVariable("id") Long id) {
     * User user = userService.getId(id);
     * userService.ban(user);
     * return "redirect:/admin/order_today/nopayment";
     * 
     * }
     */

    @GetMapping("/admin/ban/user/{id}")
    public String processBanAccountUserByIdUser(@PathVariable("id") Long id) {
        User user = userService.getId(id);
        userService.ban(user);
        return "redirect:/admin/list_users";

    }

    @GetMapping("/admin/ban/userNopayment/{id}")
    public String processBanAccountUserByIdOrder(@PathVariable("id") Integer id) {
        User user = orderSevice.findUserByIdOrder(id);
        userService.ban(user);
        return "redirect:/admin/order_today/nopayment";

    }

    @GetMapping("/admin/cancelBan/{id}")
    public String processCancelBanAccount(@PathVariable("id") Long id) {
        User user = userService.getId(id);
        userService.cancelBan(user);
        return "redirect:/admin/list_users/ban";

    }

    @GetMapping("/admin/purchasedByUser/{id}")
    public String viewPurchasedByUser(@PathVariable("id") Long id, Model model) {
        User user = userService.getId(id);
        List<OrderCustomer> listOrderCustomer = orderSevice.listOrderCustomer(user);
        model.addAttribute("listOrderCustomer", listOrderCustomer);
        Status status = orderSevice.getsIDStatus("ชำระเงินแล้ว");
        Integer count = orderSevice.countNumberOfStatus(status, user);
        model.addAttribute("count", count);
        Status status1 = orderSevice.getsIDStatus("ไม่ชำระเงิน");
        Integer count1 = orderSevice.countNumberOfStatus(status1, user);
        model.addAttribute("count1", count1);
        return "Purchased";

    }
}
