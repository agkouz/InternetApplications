# InternetApplications
Το project αποτελείται από συνδυασμό των προτεινόμενων εργασιών 1,2,3

 1) Ο χρήστης επιλέγει από έναν χάρτη την τοποθεσία του, καθώς και τη στάση για την οποία ενδιαφέρεται. Υπολογίζεται η απόσταση του από τη στάση καθώς και η επόμενη άφιξη σε αυτή τη στάση 
 
 2) Επιλογή δρομολογίου και προβολή του πάνω σε χάρτη. Υπολογισμός της απόστασης του καθώς και της επόμενης άφιξης στην αφετηρία. Επιπλέον φιλτράρισμα ανάλογα με το αν ο χρήστης θέλει τα δρομολόγια για τα οποία δεν βρέχει στην αφετηρία (με χρήση εξωτερικού API καιρού)
 
 3) Επιστροφή ημερών με τις μέγιστες μετακινήσεις για κάθε είδος καιρού - web service 12
 
Τα δεδομένα έγιναν import σε local βάση δεδομένων η οποία και έγινε deploy σε online server.

Τεχνολογίες υλοποίησης:

Front -> vue.js <br/>
Back -> spring boot <br/>
DB -> mysql


## Installation

Για την εγκατάσταση της εφαρμογής:

```bash
git clone https://github.com/CodePsychedelic/InternetApplications
cd InternetApplications
mvn clean install
cd InternetAppsFront
npm install
```

Σε περίπτωση που χρησιμοποιηθεί local βάση δεδομένων, θα χρειαστεί να γίνει import το αρχείο Database > internetapps_dump.sql και να αλλάξουν τα στοιχεία σύνδεσης, πριν γίνει η παραπάνω διαδικασία.

## Run
Στον φάκελο InternetApplications

```bash
java -jar ./target/InternetApps-1.0.0.jar
```

Στον φάκελο InternetApplications/InternetAppsFront

```bash
npm run dev
```

