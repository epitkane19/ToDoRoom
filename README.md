# Viikkotehtävä 6 Room - ToDo app

- Room mahdollistaa tavan tallentaa ja hakea lokaalia dataa androidilla. Siihen kuuluu muutamia tekijöitä kuten entity, DAO, database, repository, ViewModel ja UI.
  Entity määrittelee data modelin tietokannalle.
  DAO sisältää SQL kyselyt. 
  Database on itse tietokanta ja ottaa vastaan DAO kyselyitä.
  Repository antaa selvän API:n ViewModelille.
  ViewModel sisältää UI staten, kerää datan repositorylta ja ohjaa sen UI:lle.
  UI näyttää datan.

- Projekti sisältää data, model, view ja viewmodel kansiot. Data-kansiossa on vielä local kansio ja repository kansio tehtävän ohjeiden mukaisesti.

- Datavirran kulku: UI aktivoi toiminnan (taskin lisäys, päivitys tai poisto esimerkiksi). ViewModel saa tiedon tästä ja siirtää eteenpäin repository funktiolle. Repository kutsuu oikean DAO metodin. Dao lukee ja kirjoittaa dataa Room tietokannassa. Room palauttaa tiedon takaisin aina ViewModeliin asti mistä lopuksi UI saa tiedon ja päivittyy.

Demovideo: https://www.youtube.com/watch?v=o4c8v4bpYNs

  

