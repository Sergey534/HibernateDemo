package org.example.hibernate;

import java.util.List;
import java.util.Scanner;
import org.example.hibernate.model.Customer;
import org.example.hibernate.model.Order;
import org.example.hibernate.model.OrderKey;
import org.example.hibernate.model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class MainClass {

  public static void main(String[] args) {
    SessionFactory factory = new Configuration()
        .configure("hibernate.cfg.xml")
        .addAnnotatedClass(Customer.class)
        .addAnnotatedClass(Product.class)
        .addAnnotatedClass(Order.class)
        .addAnnotatedClass(OrderKey.class)
        .buildSessionFactory();

    Session session = null;

    try {
      Scanner scanner = new Scanner(System.in);
      System.out.println("Enter command");
      while (scanner.hasNext()) {
        String command = scanner.nextLine();
        if (command.equals("exit")) {
          break;
        }
        String[] commandParts = command.split(" ");
        Customer customer;
        Product product;
        List<Order> orders;
        switch (commandParts[0]) {
          case "/buy":
            System.out.println("/buy");
            session = factory.getCurrentSession();
            session.beginTransaction();

            customer = (Customer) session
                .createQuery("FROM Customer c WHERE c.name =:name")
                .setParameter("name", commandParts[1]).getSingleResult();
            product = (Product) session
                .createQuery("FROM Product p WHERE p.name =:name")
                .setParameter("name", commandParts[2]).getSingleResult();
            OrderKey orderKey = new OrderKey();
            orderKey.setCustomerId(customer.getId());
            orderKey.setProductId(product.getId());

            Order order = new Order();
            order.setOrderKey(orderKey);
            order.setPrice(product.getPrice());

            session.save(order);
            session.getTransaction().commit();
            System.out.println("saved the order: " + order);
            System.out.println("Enter new command");
            break;
          case "/showProductsByCustomer":
            System.out.println("/showProductsByCustomer");
            session = factory.getCurrentSession();
            session.beginTransaction();
            customer = (Customer) session
                .createQuery("FROM Customer c WHERE c.name=:name")
                .setParameter("name", commandParts[1]).getSingleResult();
            orders = customer.getOrders();
            orders.forEach(o -> System.out.println("Products for " + customer.getName() + ": " + o.getProduct()));
            session.getTransaction().commit();
            System.out.println("Enter new command");
            break;
          case "/findCustomersByProductTitle":
            System.out.println("/findCustomersByProductTitle");
            session = factory.getCurrentSession();
            session.beginTransaction();
            product = (Product) session
                .createQuery("FROM Product p WHERE p.name=:name")
                .setParameter("name", commandParts[1]).getSingleResult();
            orders = product.getOrders();
            orders.forEach(o -> System.out.println("Customers for " + product.getName() + ": " + o.getCustomer()));
            session.getTransaction().commit();
            System.out.println("Enter new command");
            break;
          case "/removeCustomer":
            System.out.println("/removeCustomer");
            session = factory.getCurrentSession();
            session.beginTransaction();

            customer = (Customer) session
                .createQuery("From Customer p WHERE p.name=:name")
                .setParameter("name", commandParts[1]).getSingleResult();

            session.delete(customer);
            System.out.println("Removed - " + customer);
            session.getTransaction().commit();
            System.out.println("Enter new command");
            break;
          case "/removeProduct":
            System.out.println("/removeProduct");
            session = factory.getCurrentSession();
            session.beginTransaction();

            product = (Product) session
                .createQuery("From Product p WHERE p.name=:name")
                .setParameter("name", commandParts[1]).getSingleResult();

            session.delete(product);
            System.out.println("Removed - " + product);
            session.getTransaction().commit();
            System.out.println("Enter new command");
            break;
        }
      }
    } finally {
      factory.close();
      session.close();
    }
  }
}
