package service;

import product.interfaces.Shippable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShippingService {

    public void processShipment(List<Shippable> items) {
        if (items == null || items.isEmpty()) {
            return;
        }

        System.out.println("\n Shipment info");

        Map<String, Integer> itemCounts = new HashMap<>();
        Map<String, Double> itemWeights = new HashMap<>();
        double totalWeight = 0.0;

        for (Shippable item : items) {
            totalWeight += item.getWeight();
            itemCounts.put(item.getName(), itemCounts.getOrDefault(item.getName(), 0) + 1);
            itemWeights.putIfAbsent(item.getName(), item.getWeight());
        }

        for (Map.Entry<String, Integer> entry : itemCounts.entrySet()) {
            String name = entry.getKey();
            Integer count = entry.getValue();
            System.out.printf("%dx %s %.0fg%n", count, name, itemWeights.get(name));
        }

        System.out.printf("Total package weight %.1fkg%n", totalWeight);
    }
}