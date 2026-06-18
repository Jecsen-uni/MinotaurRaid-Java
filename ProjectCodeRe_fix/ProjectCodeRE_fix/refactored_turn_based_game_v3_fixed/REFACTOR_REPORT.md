# Refactor Report v3

## Pemecahan God Class

`BattleManager` versi awal terlalu banyak tanggung jawab: menyimpan data battle, menampilkan UI battle, membaca input, menentukan target, memproses turn player, memproses turn enemy, menghitung status menang/kalah, dan mengatur turn order.

Pada versi ini, class di package `game` dibuat lebih sederhana menjadi 3 class utama:

| Class | Tanggung Jawab |
|---|---|
| `BattleManager` | Mengatur state battle, round, turn order, daftar player/enemy, dan status menang/kalah. |
| `BattleDisplay` | Menampilkan status battle, daftar target, damage, heal, dan pesan game. |
| `TurnProcessor` | Memproses aksi player dan enemy, termasuk skill Warrior, Archer, Mage, Minotaur, dan Minion. |

## Code Smell yang Ditemukan pada Kode Awal

| Class | Code Smell | Penjelasan | Perbaikan |
|---|---|---|---|
| `BattleManager` | God Class | Satu class mengurus hampir seluruh logic game. | Dipecah menjadi `BattleManager`, `BattleDisplay`, dan `TurnProcessor`. |
| `BattleManager` | Long Method | Method seperti `processWarriorTurn`, `processArcherTurn`, `processMageTurn`, dan `processMinotaurTurn` terlalu panjang. | Logic dipindahkan ke `TurnProcessor` dan dibuat lebih terstruktur. |
| `BattleManager` | Duplicated Code | Validasi input target dan loop pemilihan target berulang di beberapa method. | Dibuat helper `chooseEnemy()` dan `chooseTeammateExcept()` di `TurnProcessor`. |
| `BattleManager` | Mixed Responsibility | Logic tampilan, input, battle state, dan combat tercampur. | Tampilan dipisah ke `BattleDisplay`, turn/action dipisah ke `TurnProcessor`. |
| `BattleManager` | Constructor Doing Work | Battle langsung berjalan dari constructor. | Constructor hanya inisialisasi, battle dimulai lewat `startBattle()`. |
| `BattleManager` | Magic Number | Angka seperti delay, threshold HP Minotaur, dan cooldown ditulis langsung. | Dibuat constant seperti `TURN_DELAY_MS` dan `MINOTAUR_SUMMON_HP_THRESHOLD`. |
| `BattleManager` | Hardcoded Output | Round selalu tertulis `Round 1`. | Round disimpan dalam variable `round` dan naik setiap ronde. |
| `Archer` | Empty Method | `windBooster()` kosong sehingga skill tidak memiliki efek. | Skill dibuat menambah speed teammate. |
| `Sleep` | Empty Catch Block | `InterruptedException` ditangkap tetapi tidak diproses. | Diganti dengan `ConsoleUtil.sleep()` yang memanggil `Thread.currentThread().interrupt()`. |
| `Entity` | Poor Encapsulation | Banyak field protected dan setter public yang bisa mengubah state sembarangan. | Field dibuat private/final jika memungkinkan, akses lewat method terkontrol. |
| `MainMenu` | Duplicated Input Handling | Try-catch input angka berulang. | Dibantu oleh `InputHelper.readIntInRange()`. |
| `Main` | Dead Code / TODO | Masih ada komentar TODO bawaan IDE. | Dihapus. |
| `module-info` | Useless Comment | Hanya berisi komentar template. | Tidak digunakan pada versi sederhana ini. |

## Ringkasan Perubahan

1. `BattleManager` tidak lagi menjadi God Class.
2. Tampilan battle dipindahkan ke `BattleDisplay`.
3. Proses aksi player dan enemy dipindahkan ke `TurnProcessor`.
4. Input validasi dibuat lebih rapi menggunakan `InputHelper`.
5. `windBooster()` sekarang memiliki efek nyata.
6. Empty catch block pada sleep diperbaiki.
7. Round battle tidak lagi hardcoded.
8. Kode lebih mudah dibaca karena pembagian class lebih jelas.
