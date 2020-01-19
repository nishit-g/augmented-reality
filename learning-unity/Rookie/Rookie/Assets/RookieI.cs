using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class RookieI : MonoBehaviour
{

   
    //Create a gameObject for having a reference to change the color
    public GameObject cube;

    void Start()
    {
        //Get the color component of the cube
        cube.GetComponent<Renderer>().material.color = Color.red;
        
    }

    void Update()
    {

        if (Input.GetKeyDown(KeyCode.Space))
        {
            Debug.Log("This isn't supposed to work");
        }

    }
}
