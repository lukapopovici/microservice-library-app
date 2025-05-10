package com.sd.laborator.library.laborator.business.merkle

import java.security.MessageDigest

data class MerkleNode(
    val hash: String,
    val data: String? = null,
    val left: MerkleNode? = null,
    val right: MerkleNode? = null
)

class MerkleTree {
    private var root: MerkleNode? = null

    fun buildTree(data: List<String>): MerkleNode {
        if (data.isEmpty()) {
            throw IllegalArgumentException("Data list cannot be empty")
        }
        val leaves = data.map { createLeaf(it) }
        root = buildTreeRecursive(leaves)
        return root!!
    }

    private fun buildTreeRecursive(nodes: List<MerkleNode>): MerkleNode {
        when {
            nodes.isEmpty() -> throw IllegalArgumentException("Nodes list cannot be empty")
            nodes.size == 1 -> return nodes[0]
        }

        val parents = mutableListOf<MerkleNode>()

        // Process pairs of nodes
        var i = 0
        while (i < nodes.size) {
            val left = nodes[i]
            val right = if (i + 1 < nodes.size) nodes[i + 1] else left
            val parentHash = hashPair(left.hash, right.hash)
            parents.add(MerkleNode(parentHash, null, left, right))
            i += 2
        }

        if (parents.size == 1) {
            return parents[0]
        }

        return buildTreeRecursive(parents)
    }

    private fun createLeaf(data: String): MerkleNode {
        return MerkleNode(hash(data), data)
    }

    private fun hash(data: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(data.toByteArray())
        return hash.joinToString("") { "%02x".format(it) }
    }

    private fun hashPair(hash1: String, hash2: String): String {
        return hash(hash1 + hash2)
    }

    fun search(data: String): Boolean {
        val searchHash = hash(data)
        return searchRecursive(root, searchHash)
    }

    private fun searchRecursive(node: MerkleNode?, searchHash: String): Boolean {
        if (node == null) return false
        if (node.hash == searchHash) return true
        return searchRecursive(node.left, searchHash) || searchRecursive(node.right, searchHash)
    }
}