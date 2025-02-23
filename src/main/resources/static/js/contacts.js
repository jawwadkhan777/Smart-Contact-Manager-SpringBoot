console.log("Contacts.js loaded");

const viewContactModal = document.getElementById("view_contact_modal");

const baseUrl = "http://localhost:8081"
//const baseUrl = "http://scm0.eu-north-1.elasticbeanstalk.com"

// options with default values
const options = {
  placement: "bottom-right",
  backdrop: "dynamic",
  backdropClasses: "bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40",
  closable: true,
  onHide: () => {
    console.log("modal is hidden");
  },
  onShow: () => {
    console.log("modal is shown");
  },
  onToggle: () => {
    console.log("modal has been toggled");
  },
};

// instance options object
const instanceOptions = {
  id: "view_contact_modal",
  override: true,
};

const contactModal = new Modal(viewContactModal, options, instanceOptions);

function openContactModal() {
  contactModal.show();
}

function closeContactModal() {
  contactModal.hide();
}

async function loadContactModal(id) {
  console.log(id);

  try {
    const data = await (
      await fetch(`${baseUrl}/api/contacts/${id}`)
    ).json();
    console.log(data);
    // console.log(data.name);
    console.log(data.address);
    
    document.querySelector("#contact_name").innerHTML = data.name;
    document.querySelector("#contact_email").innerHTML = data.email;
    document.querySelector("#contact_image").src = data.picture;
    document.querySelector("#contact_phone").innerHTML = data.phoneNumber;
    document.querySelector("#contact_address").innerHTML = data.address;
    const desc = document.querySelector("#contact_about");
    if(!data.description == "") 
      desc.innerHTML = data.description;
    else
      desc.innerHTML = "no description"

    const fav = document.querySelector("#contact_favorite");
    if(data.favourite) 
      fav.innerHTML = "<i class='fa-solid fa-star text-yellow-300 w-6 h-6'></i><i class='fa-solid fa-star text-yellow-300 w-6 h-6'></i><i class='fa-solid fa-star text-yellow-300 w-6 h-6'></i>"; 
    else 
      fav.innerHTML = "Not favourite contact";
    

    document.querySelector("#contact_website").innerHTML = data.websiteLink;
    document.querySelector("#contact_website").href = data.websiteLink;
    document.querySelector("#contact_linkedIn").innerHTML = data.linkedInLink;
    document.querySelector("#contact_linkedIn").href = data.linkedInLink;


    openContactModal();
    
  } catch (error) {
    console.error(error);
  }
}

// delete contact
async function deleteContact(id) {
  Swal.fire({
    title: "Do you want to delete the contacts?",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "delete",
  }).then((result) => {
    /* Read more about isConfirmed, isDenied below */
    if (result.isConfirmed) {
      const url = `${baseUrl}/user/contacts/delete/${id}`;
      window.location.replace(url);
      // Swal.fire("Deleted", "", "success");
    }
  });
}

// export contacts to excel
const exportDataHandler = () => {
  TableToExcel.convert(document.getElementById("contacts-table"), {
      name: "contacts.xlsx",
      sheet: {
          name: "Sheet 1"
      }
  });
}
