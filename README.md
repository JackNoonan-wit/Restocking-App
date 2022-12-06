# Restocking-App

The current Restocking app allows the user to create a new tesco Branch and then allows the user to add products to these branches that require a restock.

### Main.kt
Stores the front end part of the app, the section you see while running

### Branch.kt
Stores the data fields for any created branch arrays. Also stores the functions called upon for commands regarding products.

### BranchAPI.kt
Stores the code required to execute the commands that make up the app but only for the branches section.

### Products.kt
Stores the data fields required for making new arrays for products.

## commands:

branch
- addBranch
- removeBranch
- updateBranch
- productOptions

product
- addProductToBranch
- removeProductFromBranch
- updateProduct

List

- listAllBranches

Search

- searchBranches
- searchBranchLocation
- searchBranchManager
