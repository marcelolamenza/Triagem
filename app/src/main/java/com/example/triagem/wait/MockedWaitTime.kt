package com.example.triagem.wait

class MockedWaitTime: CheckWaitTime {
    var currentPeopleWaiting = 80

    override fun retrievePeopleLeft(): Int {
        val exitingPeople = (1..9).random()

        currentPeopleWaiting -= exitingPeople
        if (currentPeopleWaiting < 0) {
            currentPeopleWaiting = 0
        }

        return currentPeopleWaiting
    }

    override fun retrieveTimeLeft(): Int {
        TODO("Not yet implemented")
    }
}