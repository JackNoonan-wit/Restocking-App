
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine



fun mainMenu() : Int {
    return ScannerInput.readNextInt("""
       >----------------------------------------------------------------
       >|                    TESCO RESTOCKING APPLICATION              |
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
           // 1 -> branchOptions()
           // 2 -> listOptions()
           // 3 -> searchOptions()
        }
    } while (true)
}

fun main(args: Array<String>) {
    runMenu()
}














