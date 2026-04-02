package be.groupe18.windowing.strategies.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import be.groupe18.windowing.models.CompositeDouble;
import be.groupe18.windowing.models.PRT;
import be.groupe18.windowing.models.QueryWindow;
import be.groupe18.windowing.models.Segment;
import be.groupe18.windowing.models.Vector2D;

public class SimpleQueryStrategy implements QueryStrategy {
    @Override
    public List<Segment> query(PRT tree, QueryWindow queryWindow) {
        Objects.requireNonNull(tree, "PRT arrived null in " + this.getClass().getSimpleName());
        Objects.requireNonNull(queryWindow, "Query window arrived null in " + this.getClass().getSimpleName());
        List<Segment> results = new ArrayList<>();
        search(tree, queryWindow, results);
        return results;
    }

    private void search(PRT node, QueryWindow window, List<Segment> results) {
        if (node == null || node.getSegment() == null) {
            return;
        }

        // Search with qy and qy in T. Let νsplit be the node where the two search paths split.
        PRT splitNode = findSplitNode(node, window.getYMin(), window.getYMax());
        if(splitNode == null) return;

        checkAndReport(splitNode, window, results);
        if(splitNode.isLeaf()) return;

        PRT vLeft = splitNode.getLeftChild();
        while (vLeft != null) {
            checkAndReport(vLeft, window, results);
            if (vLeft.isLeaf() || vLeft.getSegment() == null) break;

            // do if the search path goes left at ν
            if (!CompositeDouble.greaterThan(window.getYMin(), vLeft.getMedian())) {
                // then REPORTINSUBTREE(rc(ν),qx)
                reportInSubtree(vLeft.getRightChild(), window, results);
                vLeft = vLeft.getLeftChild();
            } else {
                vLeft = vLeft.getRightChild();
            }
        }

        PRT vRight = splitNode.getRightChild();
        while (vRight != null) {
            checkAndReport(vRight, window, results);
            if (vRight.isLeaf() || vRight.getSegment() == null) break;

            // do if the search path goes right at ν
            if (CompositeDouble.greaterThan(window.getYMax(), vRight.getMedian())) {
                // then REPORTINSUBTREE(lc(ν),qx)
                reportInSubtree(vRight.getLeftChild(), window, results);
                vRight = vRight.getRightChild();
            } else {
                vRight = vRight.getLeftChild();
            }
        }
    }

    private PRT findSplitNode(PRT node, CompositeDouble yMin, CompositeDouble yMax) {
        while (node != null && !node.isLeaf()) {
            // Going left
            if (!CompositeDouble.greaterThan(yMax, node.getMedian())) {
                node = node.getLeftChild();
            }
            // Going right
            else if (CompositeDouble.greaterThan(yMin, node.getMedian())) {
                node = node.getRightChild();
            }
            // Splitting
            else {
                break;
            }
        }
        return node;
    }

    private void checkAndReport(PRT node, QueryWindow window, List<Segment> results) {
        if (node != null && node.getSegment() != null) {
            Vector2D point = node.getSegment().getFirstPoint();
            // do if p(ν) ∈(−∞:qx]×[qy :qy] then report p(ν).
            if (window.contains(point)) {
                results.add(node.getSegment());
            }
        }
    }

    private void reportInSubtree(PRT node, QueryWindow window, List<Segment> results) {
        if (node == null || node.getSegment() == null) {
            return;
        }

        Vector2D point = node.getSegment().getFirstPoint();
        // if x > xMax, pruning
        if (CompositeDouble.greaterThan(point.getX(), window.getXMax())) {
            return;
        }
        if(!CompositeDouble.greaterThan(point.getX(), window.getXMin())) {
            results.add(node.getSegment());
        }
        if (!node.isLeaf()) {
            reportInSubtree(node.getLeftChild(), window, results);
            reportInSubtree(node.getRightChild(), window, results);
        }
    }
}
