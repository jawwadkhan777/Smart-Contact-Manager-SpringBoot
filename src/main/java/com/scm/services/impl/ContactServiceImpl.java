package com.scm.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.repositories.ContactRepo;
import com.scm.services.ContactService;

@Service
public class ContactServiceImpl implements ContactService {

    private ContactRepo contactRepo;
    // constructor injection
    public ContactServiceImpl(ContactRepo contactRepo) {
        this.contactRepo = contactRepo;
    }

    @Override
    public Contact save(Contact contact) {
        String contactId = UUID.randomUUID().toString();
        contact.setId(contactId);
        return contactRepo.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
        var oldContent = contactRepo.findById(contact.getId()).orElseThrow(()-> new ResourceNotFoundException());

        oldContent.setName(contact.getName());
        oldContent.setEmail(contact.getEmail());
        oldContent.setAddress(contact.getAddress());
        oldContent.setDescription(contact.getDescription());
        oldContent.setFavourite(contact.isFavourite());
        oldContent.setLinkedInLink(contact.getLinkedInLink());
        oldContent.setWebsiteLink(contact.getWebsiteLink());
        oldContent.setPhoneNumber(contact.getPhoneNumber());
        oldContent.setPicture(contact.getPicture());
        oldContent.setCloudinaryImagePublicId(contact.getCloudinaryImagePublicId());

        return contactRepo.save(oldContent);
    }

    @Override
    public List<Contact> getAll() {
        return contactRepo.findAll();
    }

    @Override
    public Contact getById(String id) {
        return contactRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Contact not found with given id: "+id));
    }

    @Override
    public void delete(String id) {
        var contact = contactRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Contact not found with given id: "+id));

        contactRepo.delete(contact);
    }

    @Override
    public List<Contact> getByUserId(String userId) {
        return contactRepo.findByUserId(userId);
    }

    @Override
    public Page<Contact> getByUser(User user, int page, int size, String sortBy, String direction) {

        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        var pageable = PageRequest.of(page, size, sort);

        return contactRepo.findByUser(user, pageable);
    }

    @Override
    public List<Contact> search(String name, String email, String phoneNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'search'");
    }

    @Override
    public Page<Contact> searchByName(String nameKeyword, int page, int size, String sortBy, String direction, User user) {
        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);

        return contactRepo.findByNameContainingAndUser(nameKeyword, user, pageable);
    }

    @Override
    public Page<Contact> searchByEmail(String emailKeyword, int page, int size, String sortBy, String direction, User user) {
        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);

        return contactRepo.findByEmailContainingAndUser(emailKeyword, user, pageable);
    }

    @Override
    public Page<Contact> searchByPhone(String phoneKeyword, int page, int size, String sortBy, String direction, User user) {
        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);

        return contactRepo.findByPhoneNumberContainingAndUser(phoneKeyword, user, pageable);
    }


}
