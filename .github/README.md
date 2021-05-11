# GitHub-workflows in Nessie

## Creating & Publishing a Nessie release

Creating a Nessie release does not need much manual interaction. To perform a release, a
"fully green" commit in the `main` branch is needed, so the statuses recorded for the git
commit that shall become a release must all have the "success" result (the green checkmark).

To initiate the release process, manually start the "Create Release" (`release-create.yml`)
GitHub workflow, which requires two input parameters:
* the next release version (e.g. `0.6.0`)
* the next development iteration version (e.g. `0.6.1`) *without* any suffix like SNAPSHOT
* the git reference to create the release from (e.g. a Git SHA) is optional, it defaults
  to HEAD of the `main` branch

The "Create Release" workflow effectively just bumps the Nessie version to the release version
in a new git commit with a new git tag plus another git commit to bump to the Nessie version
to the next development iteration version. Those two commits + the tag are pushed to the
`main` branch.

Pushing the Nessie release git-tag triggers the "Publish release" (`release-publish.yml`) 
workflow. This workflow runs in the `release` GitHub environment, which requires manual
approval (GH calls it a review). The "Publish release" workflow then creates and deploys
the release artifacts.

The "Publish release" workflow can also be started manually.

## Site-Deployment

The `site.yml` workflow is triggered when the contents of the `site/` directory on the
`main` branch has changed.

## Main-branch CI

Runs CI on the `main` branch for Maven, Gradle + Python, deploys Maven snapshot artifacts
and Docker using `nessie-unstable`. 

## Pull-Request Ci

Runs CI on all pull requests for Maven, Gradle + Python.