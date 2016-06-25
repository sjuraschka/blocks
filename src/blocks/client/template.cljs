(ns blocks.client.template)

(defmulti template
  (fn [template-id]
    template-id))

