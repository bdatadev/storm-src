;; Licensed to the Apache Software Foundation (ASF) under one
;; or more contributor license agreements.  See the NOTICE file
;; distributed with this work for additional information
;; regarding copyright ownership.  The ASF licenses this file
;; to you under the Apache License, Version 2.0 (the
;; "License"); you may not use this file except in compliance
;; with the License.  You may obtain a copy of the License at
;;
;; http://www.apache.org/licenses/LICENSE-2.0
;;
;; Unless required by applicable law or agreed to in writing, software
;; distributed under the License is distributed on an "AS IS" BASIS,
;; WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
;; See the License for the specific language governing permissions and
;; limitations under the License.
(ns backtype.storm.worker-test
  (:use [clojure test])
  (:import [backtype.storm.messaging TaskMessage IContext IConnection ConnectionWithStatus ConnectionWithStatus$Status])
  (:import [org.mockito Mockito])
  (:use [backtype.storm bootstrap testing])
  (:use [backtype.storm.daemon common])

  (:require [backtype.storm.daemon [worker :as worker]])
  )

(bootstrap)

(deftest test-worker-is-connection-ready
  (let [connection (Mockito/mock ConnectionWithStatus)]
    (. (Mockito/when (.status connection)) thenReturn ConnectionWithStatus$Status/Ready)
    (is (= true (worker/is-connection-ready connection)))

    (. (Mockito/when (.status connection)) thenReturn ConnectionWithStatus$Status/Connecting)
    (is (= false (worker/is-connection-ready connection)))

    (. (Mockito/when (.status connection)) thenReturn ConnectionWithStatus$Status/Closed)
    (is (= false (worker/is-connection-ready connection)))
  ))