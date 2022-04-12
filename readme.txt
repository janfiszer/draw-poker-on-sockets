    Rozgrywka bazuje na zasadach z tej strony
    https://pl.wikipedia.org/wiki/Poker_pi%C4%99ciokartowy_dobierany
    Nie możliwości licytacji, aby program nie wspierał hazardu :)

    Odpalanie programu:
        1. Należy odpalić serwer i podać ilość graczy
        2. Potem odpalić klientów i podać ich nicki
        3. Rozgrywka zacznie się sama

    Przebieg rozgrywki krok po kroku:
        1. Po dołączeniu klienta, proszony jest o swój podanie swojego nicku

        Przykład: jonson19

        2. Gdy dołączy wymagana liczba graczy gra sie rozpoczyna i gracze dostaja swoje karty

        Przykład:
        Those are your cards:
        1. NINE CLUB
        2. ACE CLUB
        3. TWO CLUB
        4. SEVEN CLUB
        5. KING CLUB

        3. Gracze mają możliwość wysłania (jeżeli nie chcą nic napisać należy wysłać [ENTER])

            Przykład:
            hello guys

            Wtedy inni gracze dostają wiadomość
            jonson19: hello guys

        4. Każdy z graczy zgodnie z kolejnością dołączenia, wyminia swoje karty

            Przykład:
            4 5

        Serwer zwraca jak wygląda ręka gracza po wymiannie:
            Your cards after exchange:
            1. NINE CLUB
            2. ACE CLUB
            3. TWO CLUB
            4. SIX SPADES
            5. FIVE HEARTS

        5. Po wymienieniu kart przez graczy serwer ocenia kto ma najlepszą figurę na ręce i wysyła informacje do wszystkich graczy
            Przykład:
            jeba have won!
            His best ranking is: PAIR
        6. Po rozgrywce każdy z graczy ma znowu możliwość(jeżeli nie chcą nic napisać należy wysłać [ENTER]) wysłania wiadomości i zagłosowania czy chce zagrać jeszcze raz
            Przykład:
            what a game that was

            Serwer wysyła zapytanie:
            Want to play again? (y/n)

             Przyklad odpowiedzi:
             y

             Wtedy serwer zwraca informacje:
             You have agreed! Waiting for others...

        7. Jeżeli wszyscy wyrażą zgodę rozgrywka zaczyna się jeszcze raz
