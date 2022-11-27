
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import persistence.JSONSerializer

import controllers.BranchAPI
import models.Branch
import java.io.File


private val branchAPI = BranchAPI(JSONSerializer(File("branches.json")))
fun mainMenu() : Int {
    return ScannerInput.readNextInt("""
       >----------------------------------------------------------------
       >|                 TESCO RESTOCKING APPLICATION                 |
       >----------------------------------------------------------------
       >|                           Main Menu                          |
       >|   1) Branch Options   2) List Options   3) Search Options    |
       >|                            0) Exit                           |
       >|                                                              |
       >----------------------------------------------------------------
   ==>> """.trimMargin(">"))
}

fun runMenu(){
    do {
        val option = mainMenu()
        when (option) {
            1 -> branchOptions()
            2 -> listOptions()
            3 -> searchOptions()
            else -> println("Invalid option entered: ${option}")
        }
    } while (true)
}

fun main(args: Array<String>) {
    runMenu()
}

fun branchOptions(){
    val option = readNextInt(
        """
                  > ---------------------------------
                  > |   1) Add new Branch           |
                  > |   2) Remove existing Branch   |
                  > |   3) Update existing Branch   |
                  > ---------------------------------
         > ==>> """.trimMargin(">"))

    when (option) {
        1 -> addBranch();
        2 -> removeBranch();
        3 -> updateBranch();
        else -> println("Invalid option entered: " + option);
    }
}
// Branch Options functions

fun addBranch(){
    val branchName = readNextLine("Enter a name for the branch:")
    val branchLocation = readNextLine("Enter the branch location:")
    val branchManager = readNextLine("Enter the current manager:")
    val estDate = readNextLine("Enter the branch's establishment date:")
    val isAdded = branchAPI.add(Branch(branchName, branchLocation, branchManager, estDate))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}
fun removeBranch(){
    //listBranches()
    if (branchAPI.numberOfBranches() > 0) {
        val indexToDelete = readNextInt("Enter the index of the branch to delete: ")
        val branchToDelete = branchAPI.deleteBranch(indexToDelete)
        if (branchToDelete != null) {
            println("Delete Successful! Deleted Branch: ${branchToDelete.branchName}")
        } else {
            println("Delete Failed")
        }
    }
}
fun updateBranch(){
    // listBranches()
    if (branchAPI.numberOfBranches() > 0) {
        val indexToUpdate = readNextInt("Enter the index of the note to update: ")
        if (branchAPI.isValidIndex(indexToUpdate)) {
            val branchName = readNextLine("Enter a name for the branch:")
            val branchLocation = readNextLine("Enter the branch location:")
            val branchManager = readNextLine("Enter the current manager:")
            val estDate = readNextLine("Enter the branch's establishment date:")

            if (branchAPI.updateBranch(indexToUpdate, Branch(branchName, branchLocation, branchManager, estDate))){
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no branches for this index number")
        }
    }
}







fun listOptions(){
    if (branchAPI.numberOfBranches() > 0) {
        val option = readNextInt(
            """
                  > --------------------------------
                  > |   1) List all Branches       |
                  > --------------------------------
         > ==>> """.trimMargin(">"))

        when (option) {
            1 -> listAllBranches();
            else -> println("Invalid option entered: " + option);
        }
    } else {
        println("Please Add at-least one(1) Branch before moving to this section");
    }
}
// List Options functions
fun listAllBranches() {
    println(branchAPI.listAllBranches())
}




fun searchOptions(){
    if (branchAPI.numberOfBranches() > 0) {
        val option = readNextInt(
            """
                  > -----------------------------------
                  > |   1) Search Branch by Name      |
                  > |   2) Search Branch by Location  |
                  > |   3) Search Branch by Manager   |
                  > -----------------------------------
         > ==>> """.trimMargin(">"))

        when (option) {
            1 -> searchBranches();
            2 -> searchBranchLocation();
            3 -> searchBranchManager();
            else -> println("Invalid option entered: " + option);
        }
    } else {
        println("Please Add at-least one(1) Branch before moving to this section");
    }
}
//Search Options functions
fun searchBranches() {
    val searchName = readNextLine("Please enter  the name you wish to search Branches using: ")
    val searchResults = branchAPI.searchByName(searchName)
    if (searchResults.isEmpty()) {
        println("No branches found under that name")
    } else {
        println(searchResults)
    }
}
fun searchBranchLocation() {
    val searchLocation = readNextLine("Please enter the Location you wish to search Branches using: ")
    val searchResults = branchAPI.searchByLocation(searchLocation)
    if (searchResults.isEmpty()) {
        println("No branches found with that Location")
    } else {
        println(searchResults)
    }
}
fun searchBranchManager() {
    val searchManager = readNextLine("Please enter the Manager you wish to search Branches using: ")
    val searchResults = branchAPI.searchByManager(searchManager)
    if (searchResults.isEmpty()) {
        println("No branches found with that Manager")
    } else {
        println(searchResults)
    }
}


