package controllers

import models.Branch
import persistence.Serializer

class BranchAPI(serializerType: Serializer) {
    private var serializer: Serializer = serializerType
    private var branches = ArrayList<Branch>()
    fun add(branch: Branch): Boolean {
        return branches.add(branch)
    }

    fun numberOfBranches(): Int =
        branches.size



    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }
    fun deleteBranch(indexToDelete: Int): Branch? {
        return if (isValidListIndex(indexToDelete, branches)) {
            branches.removeAt(indexToDelete)
        } else null
    }



    fun isValidIndex(index: Int) :Boolean{
        return isValidListIndex(index, branches)
    }

    fun findBranch(index: Int): Branch? {
        return if (isValidListIndex(index, branches)) {
            branches[index]
        } else null
    }

    fun updateBranch(indexToUpdate: Int, branch: Branch?): Boolean {
        val foundBranch = findBranch(indexToUpdate)

        if ((foundBranch != null) && (branch != null)) {
            foundBranch.branchName = branch.branchName
            foundBranch.branchLocation = branch.branchLocation
            foundBranch.branchManager = branch.branchManager
            foundBranch.estDate = branch.estDate
            return true
        }

        //if the note was not found, return false, indicating that the update was not successful
        return false
    }




    fun listAllBranches(): String =
        if  (branches.isEmpty()) "No branches stored"
        else formatListString(branches)



    fun searchByName (searchString : String) =
        formatListString(
            branches.filter { branch -> branch.branchName.contains(searchString, ignoreCase = true) })
    fun searchByLocation (searchString : String) =
        formatListString(
            branches.filter { branch -> branch.branchLocation.contains(searchString, ignoreCase = true) })
    fun searchByManager (searchString : String) =
        formatListString(
            branches.filter { branch -> branch.branchManager.contains(searchString, ignoreCase = true) })














    private fun formatListString(branchesToFormat : List<Branch>) : String =
        branchesToFormat
            .joinToString (separator = "\n") { branch ->
                branches.indexOf(branch).toString() + ": " + branch.toString() }
}















































