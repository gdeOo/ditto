/*
 *  This file is part of the Jikes RVM project (http://jikesrvm.org).
 *
 *  This file is licensed to You under the Eclipse Public License (EPL);
 *  You may not use this file except in compliance with the License. You
 *  may obtain a copy of the License at
 *
 *      http://www.opensource.org/licenses/eclipse-1.0.php
 *
 *  See the COPYRIGHT.txt file distributed with this work for information
 *  regarding copyright ownership.
 */
package org.jikesrvm.compilers.opt.dfsolver;

import java.util.ArrayList;

import org.jikesrvm.compilers.opt.OptimizingCompilerException;
import org.jikesrvm.compilers.opt.util.Graph;
import org.jikesrvm.compilers.opt.util.GraphNode;
import org.jikesrvm.compilers.opt.util.GraphNodeEnumeration;

/**
 * Implementation of a graph used in the guts of the dataflow equation
 * solver.
 */
class DF_Graph implements Graph {

  /**
   * The nodes of the graph.
   */
  public final ArrayList<GraphNode> nodes = new ArrayList<GraphNode>();

  /**
   * Number of nodes in the graph.
   */
  private int count = 0;

  /**
   * @return number of nodes in the graph
   */
  public int numberOfNodes() {
    return count;
  }

  /**
   * Implementation for Graph Interface.  TODO: why is this in the
   * Graph interface?
   */
  public void compactNodeNumbering() {}

  /**
   * Enumerate the nodes in the graph.
   * @return an enumeration of the nodes in the graph
   */
  public GraphNodeEnumeration enumerateNodes() {
    return new GraphNodeEnumeration() {
      private int i = 0;

      public boolean hasMoreElements() {
        return i < count;
      }

      public GraphNode next() {
        return nodes.get(i++);
      }

      public GraphNode nextElement() {
        return next();
      }
    };
  }

  /**
   * Add a node to the graph.
   * @param x the node to add
   */
  public void addGraphNode(GraphNode x) {
    x.setIndex(count);
    nodes.add(x);
    count++;
  }

  /**
   * Unsupported.  Why is this here?
   */
  public void addGraphEdge(GraphNode x, GraphNode y) {
    throw new OptimizingCompilerException("DF_Graph edges implicit");
  }
}
