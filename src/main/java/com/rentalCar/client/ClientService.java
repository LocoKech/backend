package com.rentalCar.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Client updateClient(Long id, Client clientDetails) {
        return clientRepository.findById(id)
                .map(client -> {
                    client.setFirstName(clientDetails.getFirstName());
                    client.setLastName(clientDetails.getLastName());
                    client.setPhoneNumber(clientDetails.getPhoneNumber());
                    client.setEmail(clientDetails.getEmail());
                    client.setAge(clientDetails.getAge());
                    client.setIdentifiant(clientDetails.getIdentifiant());
                    return clientRepository.save(client);
                })
                .orElseGet(() -> {
                    clientDetails.setId(id);
                    return clientRepository.save(clientDetails);
                });
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}

