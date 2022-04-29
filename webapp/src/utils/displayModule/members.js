import {confirmInscription, denyInscription} from "../api/member";

function displayInscriptions(inscriptions) {
    const listInscriptionsPage = document.querySelector("#listInscriptionsPage");
    for (let i = 0; i < inscriptions.length; i++) {
        if (inscriptions[i].state === "pending") {
            listInscriptionsPage.innerHTML += `
        <div id="inscriptionPending" class="receptionInscriptionPending">
          <div class="receptionConfirmForm">
          <form id="confirmForm">
            <div class="receptionInscriptionGrandChild">
              <p>
                ${inscriptions[i].username}
                ${inscriptions[i].lastName} 
                ${inscriptions[i].firstName}
              </p>
            </div>
            <div class="receptionInscriptionGrandChild">
                state : ${inscriptions[i].state}
            </div>
            <div class="receptionInscriptionGrandChild">
                <input type="checkbox" id="isAdmin${i}" name="isAdmin${i}">
            </div>
            <div class="receptionInscriptionGrandChild">
                <input id ="confirm${i}" type="submit" value="Confirmer inscription">
            </div>
          </form>
          </div>
            <div class="receptionRefusalForm">
             <form id="denyForm">
                <label>Raison(s) du refus : </label>
                <input class="inputDeny" id="reasonRefusal${i}" type="text">
                <input class="inputDeny" id="deny${i}" type="submit" value="X">
                </form>
            </div>
        </div>
      `;
        } else {
            listInscriptionsPage.innerHTML += `
        <div id="inscriptionPending" class="receptionInscriptionDenied">
          <div class="receptionConfirmForm">
          <form id="confirmForm">
            <div class="receptionInscriptionGrandChild">
              <p>
                ${inscriptions[i].username}
                ${inscriptions[i].lastName} 
                ${inscriptions[i].firstName}
              </p>
            </div>
            <div class="receptionInscriptionGrandChild">
                state : ${inscriptions[i].state}
            </div>
            <div class="receptionInscriptionGrandChild">
                <input type="checkbox" id="isAdmin${i}" name="isAdmin${i}">
            </div>
            <div class="receptionInscriptionGrandChild">
                <input id ="confirm${i}" type="submit" value="Confirmer inscription">
            </div>
          </form>
          </div>
        </div>
        `;
        }
    }

    for (let i = 0; i < inscriptions.length; i++) {

        const isAdmin = document.getElementById("isAdmin"+i);
        const buttonConfirm = document.getElementById("confirm"+i);
        const buttonDeny = document.getElementById("deny"+i);
        const reasonForRefusal = document.getElementById("reasonRefusal"+i).value;
        console.log(reasonForRefusal)
        // Confirm inscription
        buttonConfirm.addEventListener("click",async (e) => {
            e.preventDefault();
            await confirmInscription(inscriptions[i].username,isAdmin.checked);
        })

        // Deny inscription
        buttonDeny.addEventListener("click",async (e) => {
            e.preventDefault();
            await denyInscription(inscriptions[i].username,reasonForRefusal);
        })
    }
}

export {displayInscriptions};