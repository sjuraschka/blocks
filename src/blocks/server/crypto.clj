(ns blocks.server.crypto
  (:require
    [clojure.java.io :as io])
  (:import
    (java.security DigestInputStream MessageDigest)
    (javax.crypto Mac)
    (javax.crypto.spec SecretKeySpec)
    (org.apache.commons.codec.binary Base64)))

(defn sha256-file
  "Compute the sha256 sum of the given file, in url-safe base64"
  [f]
  (let [md (MessageDigest/getInstance "SHA-256")
        is (io/input-stream f)
        dis (DigestInputStream. is md)
        bs (byte-array 1024)]
    (loop [] (when (not= (.read dis bs 0 1024) -1) (recur)))
    (-> (.digest md)
        Base64/encodeBase64URLSafeString)))
