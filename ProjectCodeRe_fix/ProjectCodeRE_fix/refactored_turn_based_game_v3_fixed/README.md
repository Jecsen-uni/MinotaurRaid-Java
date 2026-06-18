# Turn-Based Game Refactoe

## Cara menjalankan

Dari folder project:

```bash
javac -d out $(find src -name "*.java")
java -cp out main.Main
```

## Catatan perbaikan

`MainMenu` sudah memanggil battle dengan benar melalui:

```java
new BattleManager(playerTeam, scanner).startBattle();
```

Pastikan file `MainMenu.java` yang dipakai adalah file di `src/main/MainMenu.java`, bukan file lama. Jika memakai IDE, lakukan clean/rebuild project agar class lama tidak ikut dijalankan.
